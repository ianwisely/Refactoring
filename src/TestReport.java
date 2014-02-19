import junit.framework.TestCase;
import java.util.List;
import java.util.Collection;

public class TestReport extends TestCase {

	public TestReport(String name) { 
		super(name); 
	}
	
	public void testEmptyReport() throws Exception {
		SchedulePersistence.deleteAll();	//changed Schedule to SchdulePersistance
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		assertEquals("Number of scheduled offerings: 0\n", buffer.toString());
	}
	
	public void testReport() throws Exception {
		SchedulePersistence.deleteAll();	//changed Schedule to SchdulePersistance
		Course cs101 = CoursePersistence.create("CS101", 3);	//changed Course to CoursePersistance
		CoursePersistence.update(cs101);
		Offering off1 = OfferingPersistence.create(cs101, "M10");	//changed Offering to OfferingPersistance
		OfferingPersistence.update(off1);
		Offering off2 = OfferingPersistence.create(cs101, "T9");	//changed Schedule to SchdulePersistance
		OfferingPersistence.update(off2);
		Schedule s = SchedulePersistence.create("Bob");	//changed Schedule to SchdulePersistance
		s.schedules.add(off1);
		s.schedules.add(off2);
		SchedulePersistence.update(s);
		Schedule s2 = SchedulePersistence.create("Alice");	//changed Schedule to SchdulePersistance
		s2.schedules.add(off1);
		SchedulePersistence.update(s2);
		Report report = new Report();
		StringBuffer buffer = new StringBuffer();
		report.write(buffer);
		String result = buffer.toString();
		String valid1 = "CS101 M10\n\tAlice\n\tBob\n" + "CS101 T9\n\tBob\n" + "Number of scheduled offerings: 2\n";
		String valid2 = "CS101 T9\n\tBob\n" + "CS101 M10\n\tAlice\n\tBob\n" + "Number of scheduled offerings: 2\n";
		assertTrue(result.equals(valid1) || result.equals(valid2));
	}
}