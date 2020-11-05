
DELETE from VTTM_COMPJOB_TB where jobname='cataloging';
INSERT INTO VTTM_COMPJOB_TB (jobname, jobtype, jobproperty, startexname, progressexname, endexname,  paramproperty) VALUES (
'cataloging', 'ca',
'{
	"select": [{
		"table": "vttm_video_tb",
		"alias": "catalogvideosrc",
		"where": ["idx"],
		"select": "idx as assetid, assetfilepath || ''/''|| assetfilename as filepath, volumewin || ''video/'' as  volumewin , volumeetc || ''video/'' as volumeetc "
	},{
		"table": "vttm_video_tb",
		"alias": "shotimages",
		"where": ["idx"],
		"select": "assetfilepath || ''/'' as filepath, volumewin || ''proxyshot/'' as  volumewin , volumeetc || ''proxyshot/'' as volumeetc "
	}],
	"status": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "catalogstatus"
	}],
	"start": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "catalogstarttime"
	}],
	"end": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "catalogendtime"
	}]
}', null, null, null,  '{
"paramlist":
	[{
		"type": "mainkey",
		"field": "idx"
	}]
}');



DELETE from VTTM_COMPJOB_TB where jobname='transcoding';
INSERT INTO VTTM_COMPJOB_TB (jobname, jobtype, jobproperty, startexname, progressexname, endexname,  paramproperty) VALUES (
'transcoding', 'tc', '{
	"select": [{
		"table": "vttm_video_tb",
		"alias": "convertvideosrc",
		"where": ["idx"],
		"select": "idx as assetid, ''MXF'' as videowrappertype, assetfilepath || ''/''|| assetfilename as filepath, volumewin || ''video/'' as  volumewin , volumeetc || ''video/'' as volumeetc "
	},{
		"table": "vttm_video_tb",
		"alias": "proxyvideos",
		"where": ["idx"],
		"select": "idx as idx, assetfilepath || ''/'' || objectid || ''.mp4'' as filepath, ''h264'' as mobtype , volumewin || ''proxyvideo/'' as  volumewin , volumeetc || ''proxyvideo/'' as volumeetc "
	}],
	"status": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "transcodingstatus"
	}],
	"start": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "transcodingstarttime"
	}],
	"end": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "transcodingendtime"
	}]
}', null, null, null, '{
"paramlist":
	[{
		"type": "mainkey",
		"field": "idx"
	}]
}');


DELETE from VTTM_COMPJOB_TB where jobname='convert_video';
INSERT INTO VTTM_COMPJOB_TB (jobname, jobtype, jobproperty, startexname, progressexname, endexname,  paramproperty) VALUES (
'convert_video', 'tc_video', '{
	"select": [{
		"table": "vttm_video_tb",
		"alias": "convertvideosrc",
		"where": ["idx"],
		"select": "idx as assetid, ''MXF'' as videowrappertype, assetfilepath || ''/''|| assetfilename as filepath, volumewin || ''video/'' as  volumewin , volumeetc || ''video/'' as volumeetc "
	},{
		"table": "vttm_video_tb",
		"alias": "proxyvideos",
		"where": ["idx"],
		"select": "idx as idx, assetfilepath || ''/'' || objectid || ''.mp4'' as filepath, ''h264'' as mobtype , volumewin || ''proxyvideo/'' as  volumewin , volumeetc || ''proxyvideo/'' as volumeetc "
	}],
	"status": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "transcodingstatus"
	}],
	"start": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "transcodingstarttime"
	}],
	"end": [{
		"table": "vttm_video_tb",
		"where": ["idx"],
		"update": "transcodingendtime"
	}]
}', null, null, null, '{
"paramlist":
	[{
		"type": "mainkey",
		"field": "idx"
	}]
}');




DELETE from DEMO_WORKFLOW_TB;
INSERT INTO DEMO_WORKFLOW_TB (workflowname, assetinfo, tablename, isdelworkflow) VALUES ('ca', null, null, null);
INSERT INTO DEMO_WORKFLOW_TB (workflowname, assetinfo, tablename, isdelworkflow) VALUES ('tc', null, null, null);
INSERT INTO DEMO_WORKFLOW_TB (workflowname, assetinfo, tablename, isdelworkflow) VALUES ('tc_video', null, null, null);

DELETE FROM DEMO_WORKFLOWORDER_TB;
INSERT INTO DEMO_WORKFLOWORDER_TB (workflowname, jobname, subtype, seq, pool, priority, expool) VALUES ('ca', 'cataloging', 0, 0, 0, 0, 0);
INSERT INTO DEMO_WORKFLOWORDER_TB (workflowname, jobname, subtype, seq, pool, priority, expool) VALUES ('tc', 'transcoding', 0, 0, 0, 0, 0);
INSERT INTO DEMO_WORKFLOWORDER_TB (workflowname, jobname, subtype, seq, pool, priority, expool) VALUES ('tc_video', 'convert_video', 0, 0, 0, 0, 0);


-- RESET --
DELETE FROM DEMO_COMPJOBQUEUE_TB;
DELETE FROM DEMO_COMPSERVER_TB;
DELETE FROM DEMO_COMPSERVERJOB_TB;
DELETE FROM DEMO_WORKFLOWHIS_TB;