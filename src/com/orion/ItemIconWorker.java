package com.orion;

import com.orion.io.AsyncFileWriter;
import com.orion.io.FileWriter;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

public class ItemIconWorker implements Runnable {

    private final int id;
    private final FileWriter fileWriter;

    public ItemIconWorker(int id, AsyncFileWriter writer) {
        this.id = id;
        this.fileWriter = writer;
    }

    @Override
    public void run() {
        System.out.println("Parse " + id);
        String icon = null;
        try {
            URL url = new URL(Main.URL_WOWHEAD_ITEM + id + "&xml");
            URLConnection connection = url.openConnection();
            Document doc = parseXML(connection.getInputStream());
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xPath.compile("//item/icon");
            icon = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (icon.isEmpty()) {
            return;
        }

        IconItem iconItem = new IconItem(id, icon);
        fileWriter.append(iconItem.toString());
    }

    private Document parseXML(InputStream stream) throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return doc;
    }
}
