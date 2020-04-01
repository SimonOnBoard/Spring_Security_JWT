<#include "base.ftl"/>

<#macro content>
    <p>${user.getNick()}</p>
    <p>${user.getMail()}</p>
    <#assign a = user.getBirthDay()>
    <p>${a?string.iso_m_nz}</p>
    <a href="/files">Look for files</a>
<#--    <form id="logout-form" action="/logout" method="post">-->
<#--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
<#--        <input type="submit" value="Logout"/>-->
<#--    </form>-->
</#macro>

<#macro title>
    <title>Profile</title>
</#macro>
<@display_page />
