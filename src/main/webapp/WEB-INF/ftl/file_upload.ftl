<#include "base.ftl"/>
<#macro content>
    <div class="feed-container">
        <div class="feed">
            <h1>Files</h1>
            <form id="logout-form" action="/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="Logout"/>
            </form>            <a href="/profile">Profile</a>
            <form name="post_form" id="post_form" enctype="multipart/form-data">
                <div id="namer">
                    <div id="namer-input">
                        <input type="file" id="file" name="file" placeholder="Choose your file"/>
                    </div>
                </div>
                <button type="button" class="btn btn-danger btn-lg" id="test_ajax" onclick="f()" value="Load file">Load file
                </button>
            </form>

            <div id="result">
                <table id="table">

                </table>
            </div>

            <div>
                <p>Previously uploaded files</p>
                <table>
                    <#list files as file>
                        <tr>
                            <td>${file.getOriginalFileName()}</td>
                            <td>${file.getSize()} bytes  </td>
                            <td><a href="${file.getUrl()}">Load file</a></td>
                        </tr>
                    </#list>
                </table>
            </div>
            <div>
                <#if message?has_content>
                    <p>${message}</p>
                <#else>
                </#if>
            </div>
        </div>
    </div>


    <script type="application/javascript">
        function f() {
            // Create an FormData object
            var form = $("#post_form")[0];
            var data = new FormData(form);
            console.log("form data " + data);
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "/files",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000,
                success : function(data) {
                    var a = JSON.stringify(data);
                    var b = JSON.parse(a);
                    $("#table").append(
                        "<tr>\n" +
                        "                <td>" + b.originalName +"</td>\n" +
                        "                <td>" + b.size + " bytes" +  "</td>\n" +
                        "                <td>" + "<a href=" + b.url + ">" + "Click to load" + "</a></td>\n" +
                        "            </tr>");
                    alert(b.url);
                    console.log(a);
                    console.log(b);
                },
                error : function(err) {
                    alert(err);
                }
            });
        };
    </script>

</#macro>
<#macro title>
    <title>Loading File</title>
</#macro>
<@display_page />
