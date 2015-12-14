$(document).ready(function () {

    function ChatViewModel() {

        var that = this;

        that.chatView = ko.observable(null);
        that.addContactView = ko.observable(null);

        that.chatContent = ko.observable('');
        that.message = ko.observable('');
        that.messageIndex = ko.observable(0);
        that.activePollingXhr = ko.observable(null);

        // Contacts
        that.firstname = ko.observable('');
        that.lastname = ko.observable('');
        that.users = ko.observableArray();

        var keepPolling = false;

        that.contactView = function () {
            that.addContactView(true);
            that.chatView(null);
            that.leaveChat();
        }

        that.joinChat = function () {
            that.chatView(true);
            that.addContactView(null);
            keepPolling = true;
            pollForMessages();
        }

        function pollForMessages() {
            if (!keepPolling) {
                return;
            }
            var form = $("#joinChatForm");
            that.activePollingXhr($.ajax({
                url: form.attr("action"), type: "GET", data: form.serialize(), cache: false,
                success: function (messages) {
                    for (var i = 0; i < messages.length; i++) {
                        that.chatContent(that.chatContent() + messages[i] + "\n");
                        that.messageIndex(that.messageIndex() + 1);
                    }
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        resetUI();
                        console.error("Unable to retrieve chat messages. Chat ended.");
                    }
                },
                complete: pollForMessages
            }));
            $('#message').focus();
        }

        that.postMessage = function () {
            if (that.message().trim() != '') {
                var form = $("#postMessageForm");
                $.ajax({
                    url: form.attr("action") + "?_csrf=" + $('input[name="_csrf"]').prop('value'), type: "POST",
                    data: "message=" + $("#postMessageForm input[name=message]").val(),
                    error: function (xhr) {
                        console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
                that.message('');
            }
        }

        that.leaveChat = function () {
            that.chatView(null);
            that.activePollingXhr(null);
            resetUI();
        }

        function resetUI() {
            keepPolling = false;
            that.activePollingXhr(null);
            that.message('');
            that.messageIndex(0);
            that.chatContent('');
        }

        function User(id, firstname, lastname) {
            var self = this;
            self.id = id;
            self.firstname = firstname;
            self.lastname = lastname;
        }

        that.searchContact = function () {
            if (that.firstname().trim() != '' || that.lastname().trim() != '') {
                var form = $("#searchContactForm");
                $.ajax({
                    url: form.attr("action") + "?_csrf=" + $('input[name="_csrf"]').prop('value'),
                    type: "POST",
                    data: {
                        'firstName': that.firstname(),
                        'lastName': that.lastname()
                    },
                    success: function (users) {
                        that.users([]);
                        users.forEach(function (user) {
                            that.users.push(new User(user.id, user.firstname, user.lastname));
                        });
                    },
                    error: function (xhr) {
                        console.error("Error searching contact: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
            }
        }

        that.addUser = function(){
            var form = $("#addUserForm");
            $.ajax({
                url: form.attr("action") + "?_csrf=" + $('input[name="_csrf"]').prop('value'),
                type: "POST",
                data: "id=" + this.id,
                error: function (xhr) {
                    console.error("Error adding contact: status=" + xhr.status + ", statusText=" + xhr.statusText);
                }
            });
        }
    }

    //Activate knockout.js
    ko.applyBindings(new ChatViewModel());

});


