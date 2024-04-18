package gradle.java.sample.app;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("gradle.java.sample.app")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gradle.java.sample.app")
public class RunCucumberTest {
}
