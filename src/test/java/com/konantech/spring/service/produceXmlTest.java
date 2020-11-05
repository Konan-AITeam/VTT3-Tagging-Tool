package com.konantech.spring.service;

import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class produceXmlTest {

    @Test
    public void FileReadTest() throws FileNotFoundException {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // 루트 엘리먼트
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("company");
            doc.appendChild(rootElement);

            // staff 엘리먼트
            Element staff = doc.createElement("Staff");
            rootElement.appendChild(staff);

            // 속성값 정의
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            staff.setAttributeNode(attr);

            // 속성값을 정의하는 더 쉬운 방법
            // staff.setAttribute("id", "1");

            // firstname 엘리먼트
            Element firstname = doc.createElement("firstname");
            firstname.appendChild(doc.createTextNode("Gildong"));
            staff.appendChild(firstname);

            // lastname 엘리먼트
            Element lastname = doc.createElement("lastname");
            lastname.appendChild(doc.createTextNode("Hong"));
            staff.appendChild(lastname);

            // nickname 엘리먼트
            Element nickname = doc.createElement("nickname");
            nickname.appendChild(doc.createTextNode("Mr.Hong"));
            staff.appendChild(nickname);

            // salary 엘리먼트
            Element salary = doc.createElement("salary");
            salary.appendChild(doc.createTextNode("100000"));
            staff.appendChild(salary);

            // XML 파일로 쓰기
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF - 8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File("file.xml")));

            // 파일로 쓰지 않고 콘솔에 찍어보고 싶을 경우 다음을 사용 (디버깅용)
             StreamResult resultOut = new StreamResult(System.out);

            transformer.transform(source, resultOut);
            transformer.transform(source, result);

            System.out.println("File saved !");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }
}
