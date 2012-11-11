package datastore;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;

public class SBAStore implements ISBAStore {

	SAXBuilder builder = new SAXBuilder();
	Document doc;
	XMLOutputter serializer = new XMLOutputter();

	@Override
	public ISBAObject retrieve(OID oid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OID getEntryOID() {
		// TODO Auto-generated method stub
		System.out.println("Entry element name: "
				+ doc.getRootElement().getName());
		return null;
	}

	@Override
	public void loadXML(String filePath) {
		try {
			doc = builder.build(filePath);
			System.out.println("Za³adowano XML:");
			serializer.output(doc, System.out);
		} catch (JDOMException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	public void readXML(Element root, ComplexObject parent) {
		List<Element> childrenList = root.getChildren();
		if (childrenList.size() == 0) {
			StringObject strObj = new StringObject(root.getName());
			String value = root.getValue();
			strObj.value = value;
			if (parent != null)
				parent.getChildOIDs().add(strObj.getOID());
		} else {
			ComplexObject comObj = new ComplexObject(root.getName());
			for (Element child : childrenList) {
				readXML(child, comObj);
			}
		}
	}

	@Override
	public OID generateUniqueOID() {
		return MyOID.createOID();
	}

	@Override
	public void addJavaObject(Object o, String objectName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addJavaCollection(Collection o, String collectionName) {
		// TODO Auto-generated method stub

	}

	public SAXBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(SAXBuilder builder) {
		this.builder = builder;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public XMLOutputter getSerializer() {
		return serializer;
	}

	public void setSerializer(XMLOutputter serializer) {
		this.serializer = serializer;
	}

}
