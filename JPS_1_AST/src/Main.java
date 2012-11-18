import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

import qres.QResStack;
import qres.collection.BagResult;
import qres.single.BinderResult;
import qres.single.BooleanResult;
import qres.single.DoubleResult;
import qres.single.IntegerResult;
import qres.single.StringResult;
import qres.single.StructResult;
import ast.Expression;
import ast.binary.CommaExpression;
import ast.binary.DotExpression;
import ast.binary.EqualsExpression;
import ast.binary.GreaterThanExpression;
import ast.binary.InExpression;
import ast.binary.WhereExpression;
import ast.terminal.IntegerTerminal;
import ast.terminal.NameTerminal;
import ast.terminal.StringTerminal;
import ast.unary.BagExpression;
import ast.unary.CountExpression;
import datastore.BooleanObject;
import datastore.ComplexObject;
import datastore.IntegerObject;
import datastore.MyOID;
import datastore.SBAObject;
import datastore.SBAStore;
import datastore.SimpleObject;
import datastore.test_classes.Kierowca;
import datastore.test_classes.Przesylka;
import datastore.test_classes.Samochod;
import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.result.ISingleResult;
import envs.ENVS;
import envs.ENVSFrame;

/**
 * @author £ukasz KobyliÒski (s6709)
 * @author Borys Wojciechowski (s6427)
 **/
public class Main {
	private static final String EXAMPLE_XML = "example.xml";
	private static final String EXAMPLE_XML_2 = "example_2.xml";
	private static final String ENVS_DATA = "envs_data.xml";

	public static void main(String[] args) {
		miniProjekt4();
	}

	public static void miniProjekt4() {
		ENVS envs = new ENVS();
		SBAStore store = new SBAStore();
		
		//TODO: polaczenie loadXML i readXML
		store.loadXML(ENVS_DATA);
		Element entryElement = store.getDoc().getRootElement();
		store.readXML(entryElement, null);

		// ((emp where married).book.author) union (realNumber)
		envs.init(store.getEntryOID(), store);
		QResStack qres = new QResStack();
		
		System.out.println(envs);

		//qres.push(envs.bind("emp"));
		//BagResult bag_1 = (BagResult) qres.pop();

		// ((emp.address) where number>20).(street, city)

	}

	public static void miniProjekt3() {
		SBAStore store = new SBAStore();
		/*
		 * store.loadXML(EXAMPLE_XML_2); Element entryElement =
		 * store.getDoc().getRootElement(); store.readXML(entryElement, null);
		 * System.out.println("Entry element OID: " + store.getEntryOID());
		 */

		/*
		 * System.out.println("\nWszystkie zlozone obiekty:"); for
		 * (ComplexObject co : ComplexObject.allComplexObjects) {
		 * System.out.println(co); }
		 * 
		 * System.out.println("\nWszystkie proste obiekty:"); for (SimpleObject
		 * so : SimpleObject.allSimpleObjects) { System.out.println(so); }
		 * 
		 * System.out.println("\nWszystkie obiekty:"); for (SBAObject sbao :
		 * SBAStore.allObjects) { System.out.println(sbao); }
		 */
		/*
		 * for (SBAObject values : SBAStore.allObjectsMap.values()) {
		 * System.out.println(values); }
		 */

		// Przesylka przesylka = new Przesylka(123, new Kierowca("Borys",
		// "Wojciechowski", 67, true, new Samochod("FIAT", "126p")));
		// store.addJavaObject(przesylka, przesylka.getClass().getSimpleName());

		// Samochod samochod = new Samochod("FIAT","126p");
		// Kierowca kierowca = new Kierowca("Borys", "Wojciechowski", 67, true,
		// new Samochod("FIAT","126p"));

		// store.addJavaObjectOID(samochod,
		// samochod.getClass().getSimpleName());
		// store.addJavaObjectOID(kierowca,
		// kierowca.getClass().getSimpleName());
		/*
		 * Integer i = 10; store.addJavaObjectOID(i,
		 * i.getClass().getSimpleName()); String s = "test";
		 * store.addJavaObjectOID(s, s.getClass().getSimpleName());
		 */

		Przesylka przesylka_2 = new Przesylka(234, new Kierowca("Lukasz",
				"Kobylinski", 60, true, new Samochod("Deawoo", "Tico")));
		Przesylka przesylka_3 = new Przesylka(345, new Kierowca("Stanislaw",
				"Iksinski", 85, true, new Samochod("FIAT", "Cinquecento")));

		// TODO: fikcyjny root object (nie ma polaczenia)
		ArrayList<Object> lista_przesylek = new ArrayList<Object>();
		lista_przesylek.add(przesylka_2);
		lista_przesylek.add(przesylka_3);

		store.addJavaCollection(lista_przesylek, "lista_przesylek");

		System.out.println("\nWszystkie obiekty");
		// for (SBAObject sbao : SBAStore.allObjects) {
		// System.out.println(sbao);
		// }
		for (SBAObject values : SBAStore.allObjectsMap.values()) {
			System.out.println(values);
		}

		System.out.println("Entry element OID: " + store.getEntryOID());
	}

	public static void miniProjekt1() {
		// mini-projekt 1
		Expression exampleFromPdf = new DotExpression(new DotExpression(
				new WhereExpression(new NameTerminal("osoba"),
						new NameTerminal("øonaty")),
				new NameTerminal("ksiπøka")), new NameTerminal("autor"));

		// zad. 1
		Expression expression1 = new WhereExpression(new NameTerminal("osoba"),
				new GreaterThanExpression(new CountExpression(new NameTerminal(
						"imie")), new IntegerTerminal(1)));

		// zad. 2
		Expression expression2 = new WhereExpression(new NameTerminal("firma"),
				new InExpression(new NameTerminal("lokalizacja"),
						new BagExpression(new CommaExpression(
								new StringTerminal("Warszawa"),
								new StringTerminal("£Ûdü")))));

		// zad. 3
		Expression expression3 = new InExpression(new BagExpression(
				new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2))), new BagExpression(
				new CommaExpression(new CommaExpression(new IntegerTerminal(1),
						new IntegerTerminal(2)), new IntegerTerminal(3))));

		// zad. 4
		Expression expression4 = new DotExpression(new WhereExpression(
				new NameTerminal("firma"), new EqualsExpression(
						new NameTerminal("nazwa"), new StringTerminal("XYZ"))),
				new WhereExpression(new NameTerminal("zatrudnia"),
						new EqualsExpression(new NameTerminal("nazwisko"),
								new StringTerminal("Kowalski"))));
	}

	public static void miniProjekt2() {
		QRES_zadanie_1();
		QRES_zadanie_2();
		QRES_zadanie_3();
	}

	public void QRES_example() {
		QResStack qres = new QResStack();
		qres.push(new IntegerResult(1));
		qres.push(new IntegerResult(2));
		qres.push(new IntegerResult(3));
		IntegerResult multiRight = (IntegerResult) qres.pop(); // 3
		IntegerResult multiLeft = (IntegerResult) qres.pop(); // 2
		IntegerResult multiRes = new IntegerResult(multiLeft.getValue()
				* multiRight.getValue());
		qres.push(multiRes);
		IntegerResult plusRight = (IntegerResult) qres.pop(); // 6
		IntegerResult plusLeft = (IntegerResult) qres.pop(); // 1
		IntegerResult plusRes = new IntegerResult(plusLeft.getValue()
				+ plusRight.getValue());
		qres.push(plusRes);
		qres.push(new IntegerResult(4));
		IntegerResult minusRight = (IntegerResult) qres.pop(); // 4
		IntegerResult minusLeft = (IntegerResult) qres.pop(); // 7
		IntegerResult minusRes = new IntegerResult(minusLeft.getValue()
				- minusRight.getValue());
		qres.push(minusRes);
	}

	public static void QRES_zadanie_1() {
		System.out.println("zadanie_1");

		QResStack qres1 = new QResStack();
		qres1.push(new IntegerResult(1)); // 1
		qres1.push(new DoubleResult(2.1)); // 2
		DoubleResult structRight = (DoubleResult) qres1.pop();
		IntegerResult structLeft = (IntegerResult) qres1.pop();
		StructResult s1 = new StructResult();
		s1.add(structLeft);
		s1.add(structRight);
		qres1.push(s1); // 3
		StructResult s2 = (StructResult) qres1.pop();

		List<ISingleResult> list_1 = s2.elements();

		BagResult b1 = new BagResult();
		b1.add(list_1.get(0));
		b1.add(list_1.get(1));
		qres1.push(b1); // 4

		System.out.println(s2);
		System.out.println(b1);

		qres1.push(new IntegerResult(3)); // 5
		qres1.push(new IntegerResult(4)); // 6
		IntegerResult plusRight2 = (IntegerResult) qres1.pop();
		IntegerResult plusLeft2 = (IntegerResult) qres1.pop();
		IntegerResult plusRes2 = new IntegerResult(plusLeft2.getValue()
				+ plusRight2.getValue());
		qres1.push(plusRes2); // 7
		qres1.push(new StringResult("test")); // 8
		StringResult structRight2 = (StringResult) qres1.pop();
		IntegerResult structLeft2 = (IntegerResult) qres1.pop();
		StructResult s3 = new StructResult();
		s3.add(structLeft2);
		s3.add(structRight2);
		qres1.push(s3); // 9

		System.out.println(s3);

		StructResult s4 = (StructResult) qres1.pop();
		List<ISingleResult> list_2 = s4.elements();
		BagResult b2 = new BagResult();
		b2.add(list_2.get(0));
		b2.add(list_2.get(1));
		qres1.push(b2); // 10

		System.out.println(b2);

		BagResult b3 = (BagResult) qres1.pop();
		BagResult b4 = (BagResult) qres1.pop();

		qres1.push(iloczynKartezjanski(b3, b4)); // 11

		System.out.println(iloczynKartezjanski(b3, b4));

		BagResult b6 = (BagResult) qres1.pop();
		ArrayList<ISingleResult> arr3 = (ArrayList<ISingleResult>) b6
				.getElements();
		StructResult b5res = (StructResult) arr3.get(0);
		StructResult b5res2 = (StructResult) arr3.get(1);
		StructResult b6res = (StructResult) arr3.get(2);
		StructResult b6res2 = (StructResult) arr3.get(3);

		BinderResult br1 = new BinderResult("nazwa", b5res);
		BinderResult br2 = new BinderResult("nazwa", b5res2);
		BinderResult br3 = new BinderResult("nazwa", b6res);
		BinderResult br4 = new BinderResult("nazwa", b6res2);

		BagResult b7 = new BagResult();
		b7.add(br1);
		b7.add(br2);
		b7.add(br3);
		b7.add(br4);

		qres1.push(br1); // 12

		System.out.println(b7);
	}

	public static void QRES_zadanie_2() {
		System.out.println("\nzadanie_2");

		QResStack qres = new QResStack();
		qres.push(new StringResult("ala")); // 1
		qres.push(new StringResult("ma")); // 2
		StringResult string1 = (StringResult) qres.pop();
		StringResult string2 = (StringResult) qres.pop();
		StructResult struct1 = new StructResult();
		struct1.add(string2);
		struct1.add(string1);
		qres.push(struct1); // 3

		System.out.println(struct1);

		qres.push(new StringResult("kota")); // 4

		StringResult string3 = (StringResult) qres.pop();
		StructResult struct2 = (StructResult) qres.pop();

		StringResult string4 = (StringResult) struct2.elements().get(0);
		StringResult string5 = (StringResult) struct2.elements().get(1);

		StructResult struct3 = new StructResult();
		struct3.add(string4);
		struct3.add(string5);
		struct3.add(string3);
		qres.push(struct3); // 5

		System.out.println(struct3);

		StructResult struct4 = (StructResult) qres.pop();

		List<ISingleResult> list_1 = struct4.elements();

		BagResult b1 = new BagResult();
		b1.add(list_1.get(0));
		b1.add(list_1.get(1));
		b1.add(list_1.get(2));
		qres.push(b1); // 4

		System.out.println(b1);

		qres.push(new IntegerResult(8)); // 7
		qres.push(new IntegerResult(10)); // 8
		IntegerResult multiRight = (IntegerResult) qres.pop();
		IntegerResult multiLeft = (IntegerResult) qres.pop();
		IntegerResult multiRes = new IntegerResult(multiLeft.getValue()
				* multiRight.getValue());
		qres.push(multiRes); // 9
		qres.push(new BooleanResult(false)); // 10
		BooleanResult bool1 = (BooleanResult) qres.pop();
		IntegerResult int4 = (IntegerResult) qres.pop();
		StructResult struct5 = new StructResult();
		struct5.add(int4);
		struct5.add(bool1);
		qres.push(struct5); // 11

		System.out.println(struct5);

		StructResult struct6 = (StructResult) qres.pop();
		BagResult b2 = (BagResult) qres.pop();

		qres.push(iloczynKartezjanski(b2, struct6)); // 12

		System.out.println(iloczynKartezjanski(b2, struct6));
	}

	public static void QRES_zadanie_3() {
		System.out.println("\nzadanie_3");

		QResStack qres = new QResStack();
		qres.push(new StringResult("JPS")); // 1
		qres.push(new StringResult("rules")); // 2
		StringResult string1 = (StringResult) qres.pop();
		StringResult string2 = (StringResult) qres.pop();
		StructResult struct1 = new StructResult();
		struct1.add(string2);
		struct1.add(string1);
		qres.push(struct1); // 3

		System.out.println(struct1);

		StructResult struct2 = (StructResult) qres.pop();
		List<ISingleResult> list_1 = struct2.elements();

		BagResult b1 = new BagResult();
		b1.add(list_1.get(0));
		b1.add(list_1.get(1));
		qres.push(b1); // 4

		System.out.println(b1);

		BagResult b2 = (BagResult) qres.pop();
		BinderResult br1 = new BinderResult("x", b2);
		qres.push(br1); // 5

		System.out.println(br1);

		qres.push(new DoubleResult(2.2)); // 6

		DoubleResult double1 = (DoubleResult) qres.pop();
		BinderResult br2 = (BinderResult) qres.pop();
		StructResult struct3 = new StructResult();
		struct3.add(br2);
		struct3.add(double1);

		qres.push(struct3); // 7

		qres.push(new BooleanResult(true)); // 8

		BooleanResult bool1 = (BooleanResult) qres.pop();
		StructResult struct4 = (StructResult) qres.pop();
		List<ISingleResult> list_2 = struct4.elements();

		StructResult struct5 = new StructResult();
		struct5.add(list_2.get(0));
		struct5.add(list_2.get(1));
		struct5.add(bool1);

		qres.push(struct5); // 9

		System.out.println(struct5);
	}

	public static BagResult iloczynKartezjanski(BagResult bag_1, BagResult bag_2) {

		ArrayList<ISingleResult> bag_list_1 = (ArrayList<ISingleResult>) bag_2
				.getElements();
		ArrayList<ISingleResult> bag_list_2 = (ArrayList<ISingleResult>) bag_1
				.getElements();

		StructResult sr_1 = new StructResult();
		StructResult sr_2 = new StructResult();
		StructResult sr_3 = new StructResult();
		StructResult sr_4 = new StructResult();

		sr_1.add(bag_list_1.get(0));
		sr_1.add(bag_list_2.get(0));

		sr_2.add(bag_list_1.get(0));
		sr_2.add(bag_list_2.get(1));

		sr_3.add(bag_list_1.get(1));
		sr_3.add(bag_list_2.get(0));

		sr_4.add(bag_list_1.get(1));
		sr_4.add(bag_list_2.get(1));

		BagResult bagResult = new BagResult();
		bagResult.add(sr_1);
		bagResult.add(sr_2);
		bagResult.add(sr_3);
		bagResult.add(sr_4);

		return bagResult;
	}

	public static BagResult iloczynKartezjanski(BagResult bag,
			StructResult struct) {
		List<ISingleResult> struct_list = struct.elements();
		ArrayList<ISingleResult> bag_list = (ArrayList<ISingleResult>) bag
				.getElements();

		StructResult sr_1 = new StructResult();
		StructResult sr_2 = new StructResult();
		StructResult sr_3 = new StructResult();

		sr_1.add(bag_list.get(0));
		sr_1.add(struct_list.get(0));
		sr_1.add(struct_list.get(1));

		sr_2.add(bag_list.get(1));
		sr_2.add(struct_list.get(0));
		sr_2.add(struct_list.get(1));

		sr_3.add(bag_list.get(2));
		sr_3.add(struct_list.get(0));
		sr_3.add(struct_list.get(1));

		BagResult bagResult = new BagResult();
		bagResult.add(sr_1);
		bagResult.add(sr_2);
		bagResult.add(sr_3);

		return bagResult;
	}
}
