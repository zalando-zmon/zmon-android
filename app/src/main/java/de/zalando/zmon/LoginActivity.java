package de.zalando.zmon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Strings;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.auth.CredentialsStore;
import de.zalando.zmon.client.OAuthAccessTokenService;
import de.zalando.zmon.client.ServiceFactory;
import retrofit.client.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private CheckBox mSaveCredentials;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getActionBar() != null) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mSaveCredentials = (CheckBox) findViewById(R.id.save_credentials);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        CredentialsStore credentialsStore = new CredentialsStore(this);
        Credentials credentials = credentialsStore.getCredentials();

        if (!Strings.isNullOrEmpty(credentials.getUsername()) && !Strings.isNullOrEmpty(credentials.getPassword())) {
            mUsernameView.setText(credentials.getUsername());
            mPasswordView.setText(credentials.getPassword());
        }

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean saveCredentials = mSaveCredentials.isChecked();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password, saveCredentials);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private final boolean mSaveCredentials;

        UserLoginTask(String username, String password, boolean saveCredentials) {
            mUsername = username;
            mPassword = password;
            mSaveCredentials = saveCredentials;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final OAuthAccessTokenService OAuthAccessTokenService = ServiceFactory.createZmonLoginService(LoginActivity.this);
            final Response loginResponse = OAuthAccessTokenService.login();

            if (loginResponse.getStatus() >= 200 && loginResponse.getStatus() < 300) {
                // TODO improve status code handling!

                CredentialsStore credentialsStore = new CredentialsStore(LoginActivity.this);

                if (mSaveCredentials) {
                    credentialsStore.setCredentials(new Credentials(mUsername, mPassword));
                }

                try {
                    String accessToken = IOUtils.toString(loginResponse.getBody().in());
                    credentialsStore.setAccessToken(accessToken);
                    Log.i("[login]", "Successfully logged in: " + accessToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                final Intent zmonStatusIntent = new Intent(LoginActivity.this, ZmonStatusActivity.class);
                startActivity(zmonStatusIntent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

