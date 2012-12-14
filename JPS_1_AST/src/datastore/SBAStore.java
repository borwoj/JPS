package datastore;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
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

	public static LinkedHashMap<OID, SBAObject> allObjectsMap = new LinkedHashMap<OID, SBAObject>();
	// public static ArrayList<SBAObject> allObjects = new
	// ArrayList<SBAObject>();

	SAXBuilder builder = new SAXBuilder();
	Document doc;
	XMLOutputter serializer = new XMLOutputter();

	OID entryOID;

	@Override
	public ISBAObject retrieve(OID oid) {
		return allObjectsMap.get(oid);
		// SBAObject ret = null;
		// for (SBAObject sbao : allObjects) {
		// if (oid == sbao.getOID()) {
		// ret = sbao;
		// }
		// }
		// return ret;
	}

	@Override
	public OID getEntryOID() {
		return entryOID;
	}

	@Override
	public void loadXML(String filePath) { 
		try {
			doc = builder.build(filePath);
			//System.out.println("Za³adowano XML:");
			//serializer.output(doc, System.out);
		} catch (JDOMException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
		
		Element entryElement = getDoc().getRootElement();
		readXML(entryElement, null);

	}

	public void readXML(Element root, ComplexObject parent) {

		List<Element> childrenList = root.getChildren();
		if (childrenList.size() == 0) {

			String value = root.getValue();

			SimpleObject simpleObj;

			if (value.matches("([\\d]+[.|,][\\d]+)")) {
				simpleObj = new DoubleObject(root.getName(),
						Double.parseDouble(value.replace(",", ".")));
			} else if (value.matches("[\\d]+")) { // TODO ujemny
				simpleObj = new IntegerObject(root.getName(),
						Integer.parseInt(value));
			} else if (value.matches("(true)|(false)")) {
				simpleObj = new BooleanObject(root.getName(),
						Boolean.parseBoolean(value));
			} else {
				simpleObj = new StringObject(root.getName(), value);
			}

			if (parent != null) {
				parent.getChildOIDs().add(simpleObj.getOID());
			} else
				entryOID = simpleObj.getOID();
		}

		else {
			ComplexObject comObj = new ComplexObject(root.getName());

			if (parent != null) {
				parent.getChildOIDs().add(comObj.getOID());
			} else
				entryOID = comObj.getOID();

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
		OID moid = identifyType(o, objectName);
		if (allObjectsMap.size() == 1)
			entryOID = moid;
	}

	@Override
	public void addJavaCollection(Collection o, String collectionName) {
		ComplexObject root = new ComplexObject(collectionName);
		if (allObjectsMap.size() == 1)
			entryOID = root.getOID();
		for (Object e : o) {
			root.getChildOIDs().add(
					identifyType(e, e.getClass().getSimpleName()));
		}
	}

	public OID identifyType(Object o, String objectName) {
		SimpleObject simpleObj; // czy kolekcja

		if (o instanceof Double) {
			simpleObj = new DoubleObject(objectName, (Double) o);
			return simpleObj.getOID();
		} else if (o instanceof Integer) {
			simpleObj = new IntegerObject(objectName, (Integer) o);
			return simpleObj.getOID();
		} else if (o instanceof Boolean) {
			simpleObj = new BooleanObject(objectName, (Boolean) o);
			return simpleObj.getOID();
		} else if (o instanceof String) {
			simpleObj = new StringObject(objectName, (String) o);
			return simpleObj.getOID();
		} else {
			ComplexObject root = new ComplexObject(objectName);

			try {
				for (Field field : o.getClass().getFields()) {
					root.getChildOIDs().add(
							identifyType(field.get(o), field.getName()));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return root.getOID();
		}
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
