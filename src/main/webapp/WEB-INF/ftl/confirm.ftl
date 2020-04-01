<#include "base.ftl"/>

<#macro content>
    <div class="my-container">
        <div class="reg-container">
            <p>${result}</p>
        </div>
        <a href="/profile">Go to profile</a>
    </div>

</#macro>
<#macro title>
    <title>Confirmation</title>
</#macro>
<@display_page />
