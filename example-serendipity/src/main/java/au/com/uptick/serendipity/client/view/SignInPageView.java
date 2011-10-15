/**
 * (C) Copyright 2010, 2011 upTick Pty Ltd
 *
 * Licensed under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package au.com.uptick.serendipity.client.view;

import au.com.uptick.serendipity.client.presenter.SignInPagePresenter;
import au.com.uptick.serendipity.client.view.handlers.SignInPageUiHandlers;
import au.com.uptick.serendipity.shared.FieldVerifier;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class SignInPageView extends ViewWithUiHandlers<SignInPageUiHandlers> implements
    SignInPagePresenter.MyView {

  private static final String DEFAULT_USER_NAME = "Administrator";
  private static final String DEFAULT_PASSWORD = "N0More$ecrets";
//  private static final String DEFAULT_USER_NAME = "admin";
//  private static final String DEFAULT_PASSWORD = "admin";

  private static String html = "<div>\n"
    + "<table align=\"center\">\n"
    + "  <tr>\n" + "<td>&nbsp;</td>\n" + "<td>&nbsp;</td>\n" + "</tr>\n"
    + "  <tr>\n" + "<td>&nbsp;</td>\n" + "<td>&nbsp;</td>\n" + "</tr>\n"
    + "  <tr>\n" + "<td>&nbsp;</td>\n" + "<td>&nbsp;</td>\n" + "</tr>\n"
    + "  <tr>\n" + "<td>&nbsp;</td>\n" + "<td>&nbsp;</td>\n" + "</tr>\n"
    + "  <tr>\n" + "<td>&nbsp;</td>\n" + "<td>&nbsp;</td>\n" + "</tr>\n"
    + "  <tr>\n"
    + "    <td colspan=\"2\" style=\"font-weight:bold;\">Sign In <img src=\"images/signin.gif\"/></td>\n"
    + "  </tr>\n"
    + "  <tr>\n"
    + "    <td>User name</td>\n"
    + "    <td id=\"userNameFieldContainer\"></td>\n"
    + "  </tr>\n"
    + "  <tr>\n"
    + "    <td>Password</td>\n"
    + "    <td id=\"passwordFieldContainer\"></td>\n"
    + "  </tr>\n"
    + "  <tr>\n"
    + "    <td></td>\n"
    + "    <td id=\"signInButtonContainer\"></td>\n"
    + "  </tr>\n"
    + "  <tr>\n" + "<td>&nbsp;</td>\n" + "<td>&nbsp;</td>\n" + "</tr>\n"
    + "  <tr>\n"
    + "    <td colspan=\"2\">Forget your password?</td>\n"
    + "  </tr>\n"
    + "  <tr>\n"
    + "    <td colspan=\"2\">Contact your Serendipity administrator.</td>\n"
    + "  </tr>\n"
    + "</table>\n"
    + "</div>\n";

  private HTMLPanel panel;

  private final TextBox userNameField;
  private final PasswordTextBox passwordField;
  private final Button signInButton;

  public SignInPageView() {
    
    panel = new HTMLPanel(html);
    
    userNameField = new TextBox();
    passwordField = new PasswordTextBox();
    signInButton = new Button("Sign in");

    userNameField.setText(DEFAULT_USER_NAME);

    // See FieldVerifier
    // Passwords must contain at least 8 characters with at least one digit,
    // one upper case letter, one lower case letter and one special symbol
    passwordField.setText(DEFAULT_PASSWORD);

    panel.add(userNameField, "userNameFieldContainer");
    panel.add(passwordField, "passwordFieldContainer");
    panel.add(signInButton, "signInButtonContainer");

    bindCustomUiHandlers();
  }

  protected void bindCustomUiHandlers() {

    signInButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {

        if (FieldVerifier.isValidUserName(getUserName()) &&
           (FieldVerifier.isValidPassword(getPassword()))) {
          if (getUiHandlers() != null) {
            getUiHandlers().onOkButtonClicked();
          }
        }
        else {
          event.cancel();
          SC.say("Sign in", "You must enter a valid User name and Password.");
          resetAndFocus();
        }
      }
    });
  }

  public Widget asWidget() {
    return panel;
  }

  public String getUserName() {
    return userNameField.getText();
  }

  public String getPassword() {
    return passwordField.getText();
  }

  public void resetAndFocus() {
    userNameField.setFocus(true);
    userNameField.selectAll();
  }
}