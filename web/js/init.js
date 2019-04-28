// Cookies
function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else var expires = "";

    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}

Element.prototype.remove = function() {
    this.parentElement.removeChild(this);
};

NodeList.prototype.remove = HTMLCollection.prototype.remove = function() {
    for(var i = this.length - 1; i >= 0; i--) {
        if(this[i] && this[i].parentElement) {
            this[i].parentElement.removeChild(this[i]);
        }
    }
};

var links;
var token = "5bb440a0282726a6e1cf802b0a2ff82a";
function produceOutput (submissionID) {
    setTimeout(function () {
        $.ajax({
            url: links.codeStatus + submissionID,
            type: "GET",
            data: {
                "access_token": token,
                "withOutput": true,
                "withStderr": true,
                "withCmpinfo": true
            },
            success: function(json) {
                var data = JSON.parse(json);
                var status = data.status;
                if (!status) {
                    var result = data.result;
                    if (result == 15) {
                        document.getElementById("output_area").innerHTML = "Successfully Executed!" + "\<br/\>Time: " + data.time + " seconds \<br/\>Memory: " + data.memory + " KB\<br\>Program Output: " + data.output;
                    } else if (result == 11) {
                        document.getElementById("output_area").innerHTML = "Compilation Error!\<br/\>" + data.cmpinfo;
                    } else if (result == 12) {
                        document.getElementById("output_area").innerHTML = "Runtime Error!\<br/\>" + data.cmpinfo;
                    } else if (result == 13) {
                        document.getElementById("output_area").innerHTML = "Time Limit Exceeded!\<br/\>";
                    }  else if (result == 17) {
                        document.getElementById("output_area").innerHTML = "Memory Limit Exceeded!\<br/\>";
                    } else if (result == 19) {
                        document.getElementById("output_area").innerHTML = "Illegal System Call!\<br/\>";
                    } else if (result == 20) {
                        document.getElementById("output_area").innerHTML = "Internal Error!\<br/\>";
                    }
                    return;
                } else {
                    produceOutput(submissionID);
                }
            },
            error:function(exception) {
                alert('Exception in produceOutput():'+ exception);
            }
        });
    }, 3000)
}


(function ($) {
    $(function () {
        $('.button-collapse').sideNav({
                menuWidth: 200, // Default is 240
                edge: 'right', // Choose the horizontal origin
                closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
                draggable: true // Choose whether you can drag to open on touch screens
            }
        );
        $('.modal-trigger').leanModal();

        $.ajax({
            url: "/rsc/service_description",
            type: "GET",
            success: function(data) {
                links = data;
                console.log("Service Description: " + links);
            },
            error:function(exception){alert('Exception:'+ exception);}
        });

        $('#login_button').click(function() {
            var username = $('#user_name').val();
            var password = $('#password').val();
            var document = {
                "email": "abc@xyz.com",
                "username": username,
                "name": "dummy",
                "password": password
            };
            $.ajax({
                url: links.authenticate,
                type: "POST",
                data: JSON.stringify(document),
                contentType: "application/json",
                success: function(data) {
                    console.log('success', data);
                    createCookie("username", username, 7);
                    window.location.href = "user.html";
                },
                dataType: "application/json",
                error:function(exception){alert('Exception:'+ exception);}
            });
        });

        $('#logout_button').click(function() {
            var document = {
                "email": "abc@xyz.com",
                "username": readCookie("username"),
                "name": "dummy",
                "password": "dummy"
            };
            $.ajax({
                url: links.logOut,
                type: "POST",
                data: JSON.stringify(document),
                contentType: "application/json",
                success: function(data) {
                    eraseCookie("username");
                    window.location.href = "login.html";
                },
                dataType: "application/json",
                error:function(exception){alert('Exception in logout button:'+ exception);}
            });
        });

        $('#register_submit').click(function() {
            var document = {
                "email": $('#email').val(),
                "username": $('#reg_user_name').val(),
                "name": $('#reg_name').val(),
                "password": $('#reg_password').val()
            }
            $.ajax({
                url: links.registerUser,
                type: "POST",
                data: JSON.stringify(document),
                contentType: "application/json",
                success: function(data) {
                    console.log('success', data);
                },
                dataType: "application/json",
                error:function(exception){alert('Exception:'+ exception);}
            });
        });


        var editor = ace.edit("editor");
        editor.setTheme("ace/theme/monokai");
        editor.getSession().setMode("ace/mode/c_cpp");
        $('#file_save').click(function() {
            var editorData = editor.getSession().getValue();
            var filename = $('#save_filename').val();
            var document = {
                "username": readCookie("username"),
                "fileName": filename,
                "data": editorData
            };
            $.ajax({
                url: links.saveFile,
                type: "POST",
                data: JSON.stringify(document),
                contentType: "application/json",
                success: function(data) {
                    console.log('success', data);
                },
                dataType: "application/json",
                error:function(exception){alert('Exception:'+ exception);}
            });
        });

        $('#file_open').click(function() {
            var filename = $('#open_filename').val();
            var username = readCookie("username");
            $.ajax({
                url: links.loadFile + username + "/" + filename,
                type: "GET",
                success: function(data) {
                    editor.setValue(data.data);
                },
                error:function(exception){alert('Exception:'+ exception);}
            });
        });

        $('#profile_btn').click(function() {
            var path = links.userDetails + readCookie("username");
            $.ajax({
                url: path,
                type: "GET",
                success: function(data) {
                    var email = data.email;
                    var name = data.name;
                    var username = data.username;

                    var list_email = document.createElement("li");
                    list_email.setAttribute("id", "slide_email");
                    var para_email = document.createElement("p");
                    para_email.appendChild(document.createTextNode(email));
                    list_email.appendChild(para_email);

                    var list_name = document.createElement("li");
                    list_name.setAttribute("id", "slide_name");
                    var para_name = document.createElement("p");
                    para_name.appendChild(document.createTextNode(name));
                    list_name.appendChild(para_name);

                    var list_uname = document.createElement("li");
                    list_uname.setAttribute("id", "slide_uname");
                    var para_uname = document.createElement("p");
                    para_uname.appendChild(document.createTextNode(username));
                    list_uname.appendChild(para_uname);

                    var element = document.getElementById("slide_profile");
                    element.appendChild(list_name);
                    element.appendChild(list_uname);
                    element.appendChild(list_email);
                },
                error:function(exception){alert('Exception:'+ exception);}
            });
            document.getElementById("slide_email").remove();
            document.getElementById("slide_name").remove();
            document.getElementById("slide_uname").remove();
        });

        $('#compile_button').click(function() {
            var code = editor.getSession().getValue().trim();
            var input = $("#input_txt_area").val().trim();
            var submissionID;
            $.ajax({
                url: links.submitCode + token,
                type: "POST",
                data: {
                    "sourceCode": code,
                    "language": 44,
                    "input": input
                },
                success: function(data) {
                    var json = JSON.parse(data);
                    submissionID = parseInt(json.id);
                    produceOutput(submissionID);
                },
                error:function(exception) {
                    alert('Exception in compile button:'+ exception);
                }
            });
        });
    }); // end of document ready
})(jQuery); // end of jQuery name space