<#include "base.ftl"/>

<#macro content>
    <form name='f' action="/login" method='POST'>
        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='email'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password' /></td>
            </tr>
            <tr>
                <td><input name="submit" type="submit" value="submit" /></td>
            </tr>
        </table>
    </form>
</#macro>
<#macro title>
    <title>Login</title>
</#macro>
<@display_page />