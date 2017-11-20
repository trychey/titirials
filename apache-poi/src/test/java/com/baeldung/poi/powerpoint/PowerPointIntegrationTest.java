package com.baeldung.poi.powerpoint;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class PowerPointIntegrationTest {

	private PowerPointHelper pph;
	private String fileLocation;
	private static final String FILE_NAME = "presentation.pptx";

	@Before
	public void setUp() throws Exception {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		fileLocation = path.substring(0, path.length() - 1) + FILE_NAME;

		pph = new PowerPointHelper();
		pph.createPresentation(fileLocation);
	}

	@Test
	public void whenReadingAPresentation_thenOK() throws Exception {
		XMLSlideShow xmlSlideShow = pph.readingExistingSlideShow(fileLocation);

		Assert.assertNotNull(xmlSlideShow);
		Assert.assertEquals(4, xmlSlideShow.getSlides().size());
	}

	@Test
	public void whenSortingSlides_thenOK() throws Exception {
		XMLSlideShow xmlSlideShow = pph.readingExistingSlideShow(fileLocation);
		XSLFSlide slide4 = xmlSlideShow.getSlides().get(3);
		pph.reorderSlide(xmlSlideShow, 3, 1);

		Assert.assertEquals(slide4, xmlSlideShow.getSlides().get(1));
	}

	@Test
	public void givenPresentation_whenDeletingASlide_thenOK() throws Exception {
		XMLSlideShow xmlSlideShow = pph.readingExistingSlideShow(fileLocation);
		pph.deleteSlide(xmlSlideShow, 3);

		Assert.assertEquals(3, xmlSlideShow.getSlides().size());
	}

	@After
	public void tearDown() throws Exception {
		File testFile = new File(fileLocation);
		if (testFile.exists()) {
			testFile.delete();
		}
		pph = null;
	}
}
