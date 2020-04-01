<#include "base.ftl"/>

<#macro content>
    <div class="my-container">
        <div class="reg-container">


            <form name="reg" action="/registration" method="post"><h1>
                    Just a registration form! PUT some information IN)</h1>
                <small class="form-text text-white" style="margin-bottom: 1.7rem;"><a href="/login">Sign in</a></small>

                <div id="namer">
                    <div id="namer-input">
                        <label>Nick</label>
                        <input type="text" id="nick" name="nick" placeholder="Type your nick" required>
                    </div>
                </div>

                <div id="namer">
                    <div id="namer-input">
                        <label>password</label>
                        <input type="password" id="password" name="password" placeholder="Type your password" required>
                    </div>
                </div>

                <div id="namer">
                    <div id="namer-input">
                        <label>mail to confirm</label>
                        <input type="text" id="email" name="email" placeholder="Type your email" required/>
                    </div>
                </div>

                <div id="namer">
                    <div id="namer-input">
                        <label>Login</label>
                        <input type="text" id="login" name="login" placeholder="Type your login" required>
                    </div>
                </div>

                <div id="namer">
                    <div id="namer-input">
                        <input type="date" id="birthDay" name="birthDay"
                               min="1900-01-01" max="2020-01-01">
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value= "${_csrf.token}"/>
                <button type="submit" class="btn btn-danger btn-lg">Sign up</button>
            </form>
        </div>
    </div>

</#macro>
<#macro title>
    <title>Registration</title>
</#macro>
<@display_page />

