package com.konantech.spring.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class FFmpegUtil {

	private static Logger logger = LoggerFactory.getLogger(FFmpegUtil.class);

	@Value("${cmd.ffprobe}")
	private String ffprobeCmd;

	public String getMediaInfo(String sourceFile) {
		String query = ffprobeCmd + sourceFile;
		String mediainfo = getExecString(query);
		if(mediainfo.length() < 10) {
			mediainfo = null;
		}
		return mediainfo;
	}

	public Map<String,Object> shotSize(String mediainfo) throws Exception {
		Map<String, Object> item = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		if(mediainfo != null) {
			HashMap<String, Object> map = mapper.readValue(mediainfo, new HashMap<String, Object>().getClass());
			JSONArray jsonArray = JSONArray.fromObject(map.get("streams"));
			if(jsonArray != null && jsonArray.size() > 0) {
				for (int pos = 0; pos < jsonArray.size(); pos++) {
					JSONObject o1 = (JSONObject) jsonArray.get(pos);
					int width = MapUtils.getIntValue(o1,"width");
					int height = MapUtils.getIntValue(o1,"height");
					if(width > 0) {
						item.put("shotWidth",width);
						item.put("shotHeight",height);
						break;
					}
				}
			}
		}
		return item;
	}


	public String getExecString(String query) {
		String[] cmd = StringUtils.split(query , "|");
		Process oProcess;
		String inputLine;
		StringBuilder successBuilder = new StringBuilder();
		StringBuilder errorBuilder = new StringBuilder();
		try {
			oProcess = new ProcessBuilder(cmd).start();
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream(), "UTF-8"));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream(), "UTF-8"));
			while ((inputLine = stdOut.readLine()) != null) {
				successBuilder.append(inputLine);
				successBuilder.append("\n");
			}
			while ((inputLine = stdError.readLine()) != null) {
				errorBuilder.append(StringUtils.trim(inputLine));
				errorBuilder.append("\n");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
//		String errorMsg = errorBuilder.toString();
		return successBuilder.toString();
	}

}

