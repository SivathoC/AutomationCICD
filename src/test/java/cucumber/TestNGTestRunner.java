package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features= "src/test/java/cucumber",
				glue= "sivathoconjwaacademy.setpDefinitions",
				monochrome= true,
				tags="@Regression",
				plugin= {"html:target/cucmber.html"})

public class TestNGTestRunner extends AbstractTestNGCucumberTests{

}
