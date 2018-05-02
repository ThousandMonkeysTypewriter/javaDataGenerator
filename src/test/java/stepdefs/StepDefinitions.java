package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by thilinaga on 7/3/2017.
 */
public class StepDefinitions {
  @Given("^I am on the \"([^\"]*)\" page on URL \"([^\"]*)\"$")
  public void i_am_on_the_page_on_URL(String arg1, String arg2) throws Throwable {
    System.err.println("i_am_on_the_page_on_URL:"+arg1+" "+arg2);
//    throw new PendingException();
  }

  @When("^I fill in \"([^\"]*)\" with \"([^\"]*)\"$")
  public void i_fill_in_with(String arg1, String arg2) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    System.err.println("i_fill_in_with:"+arg1+" "+arg2);
//    throw new PendingException();
  }

  @When("^I click on the \"([^\"]*)\" button$")
  public void i_click_on_the_button(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    System.err.println("i_click_on_the_button:"+arg1);
//    throw new PendingException();
  }

  @Then("^I should see \"([^\"]*)\" message$")
  public void i_should_see_message(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    System.err.println("i_should_see_message:"+arg1);
//    throw new PendingException();
  }

  @Then("^I should see the \"([^\"]*)\" button$")
  public void i_should_see_the_button(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    System.err.println("i_should_see_the_button:"+arg1);
//    throw new PendingException();
  }
}