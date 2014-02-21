import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

public class TestSchedule extends TestCase {
	
	public TestSchedule(String name) {
		super(name);
	}

	public void testMinCredits() {
		Schedule schedule = new Schedule("name");
		Collection<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
	}
	
	public void testJustEnoughCredits() {
		Course cs110 = new Course("CS110", 11);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.addOffering(mwf10);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too few credits"));
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 12);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.addOffering(th11);
		analysis = schedule.analysis();
		assertEquals(0, analysis.size());
	}

	public void testMaxCredits() {
		Course cs110 = new Course("CS110", 20);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.addOffering(mwf10);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule.allowStudentExtraCredits(true);
		analysis = schedule.analysis();
		assertEquals(0, analysis.size());
	}

	public void testJustBelowMax() {
		Course cs110 = new Course("CS110", 19);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Schedule schedule = new Schedule("name");
		schedule.addOffering(mwf10);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Too many credits"));
		schedule = new Schedule("name");
		Course cs101 = new Course("CS101", 18);
		Offering th11 = new Offering(1, cs101, "T11,H11");
		schedule.addOffering(th11);
		analysis = schedule.analysis();
		assertEquals(0, analysis.size());
	}

	public void testDupCourses() {
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		Offering th11 = new Offering(1, cs110, "T11,H11");
		Schedule schedule = new Schedule("name");
		schedule.addOffering(mwf10);
		schedule.addOffering(th11);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Same course twice - CS110"));
	}
	
	public void testOverlap() {
		Schedule schedule = new Schedule("name");
		Course cs110 = new Course("CS110", 6);
		Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
		schedule.addOffering(mwf10);
		Course cs101 = new Course("CS101", 6);
		Offering mixed = new Offering(1, cs101, "M10,W11,F11");
		schedule.addOffering(mixed);
		List<String> analysis = schedule.analysis();
		assertEquals(1, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		Course cs102 = new Course("CS102", 1);
		Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
		schedule.addOffering(mixed2);
		analysis = schedule.analysis();
		assertEquals(3, analysis.size());
		assertTrue(analysis.contains("Course overlap - M10"));
		assertTrue(analysis.contains("Course overlap - W10"));
		assertTrue(analysis.contains("Course overlap - F11"));
	}

	public void testCourseCreate() throws Exception {
		CoursePersistence.create("CS202", 1);
		Course c2 = CoursePersistence.find("CS202");
		assertEquals("CS202", c2.getName());
		Course c3 = CoursePersistence.find("Nonexistent");
		assertNull(c3);
	}

	public void testOfferingCreate() throws Exception {
		Course c = CoursePersistence.create("CS202", 2);
		Offering offering = OfferingPersistence.create(c, "M10");
		assertNotNull(offering);
	}

	public void testPersistentSchedule() throws Exception {
		Schedule s = SchedulePersistence.create("Bob");
		assertNotNull(s);
	}

	public void testScheduleUpdate() throws Exception {
		Course cs101 = CoursePersistence.create("CS101", 3);
		CoursePersistence.update(cs101);
		Offering off1 = OfferingPersistence.create(cs101, "M10");
		OfferingPersistence.update(off1);
		Offering off2 = OfferingPersistence.create(cs101, "T9");
		OfferingPersistence.update(off2);
		Schedule s = SchedulePersistence.create("Bob");
		s.addOffering(off1);
		s.addOffering(off2);
		SchedulePersistence.update(s);
		Schedule s2 = SchedulePersistence.create("Alice");
		s2.addOffering(off1);
		SchedulePersistence.update(s2);
		Schedule s3 = SchedulePersistence.find("Bob");
		assertEquals(2, s3.offerings.size());
		Schedule s4 = SchedulePersistence.find("Alice");
		assertEquals(1, s4.offerings.size());
	}
}