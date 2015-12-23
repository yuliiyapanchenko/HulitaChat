$(document).ready(function () {

    function ChatViewModel() {

        var that = this;

        //Views
        that.publicChatView = ko.observable(null);
        that.privateChatView = ko.observable(null);
        that.addContactView = ko.observable(null);
        that.userContactsView = ko.observable(null);
        that.newConversationView = ko.observable(null);
        that.views = ko.observableArray([
            that.publicChatView,
            that.privateChatView,
            that.addContactView,
            that.userContactsView,
            that.newConversationView]);

        function setActiveView(view) {
            that.views().forEach(function (v) {
                if (v == view)
                    v(true);
                else
                    v(null);
            });
        }

        // Public chat
        that.chatContent = ko.observable('');
        that.message = ko.observable('');
        that.messageIndex = ko.observable(0);
        that.activePollingXhr = ko.observable(null);

        // Contacts
        that.firstname = ko.observable('');
        that.lastname = ko.observable('');
        that.users = ko.observableArray();
        that.userContacts = ko.observableArray();

        //Conversations
        that.selectedContacts = ko.observableArray();
        that.title = ko.observable(' ');
        that.firstMessage = ko.observable('');
        that.activePollingPrivateXhr = ko.observable(null);
        that.privateChatContent = ko.observable('');
        that.privateMessage = ko.observable('');
        that.activeConversation = ko.observable(null);

        var keepPolling = false;

        ///////////////////////////////////////////////
        that.joinChat = function () {
            setActiveView(that.publicChatView);
            keepPolling = true;
            pollForMessages();
        };

        function pollForMessages() {
            if (!keepPolling) {
                return;
            }
            var form = $("#joinChatForm");
            that.activePollingXhr($.ajax({
                url: form.attr("action"),
                type: "GET",
                data: form.serialize(),
                cache: false,
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
                    url: form.attr("action") + "?_csrf=" + $('input[name="_csrf"]').prop('value'),
                    type: "POST",
                    data: "message=" + $("#postMessageForm input[name=message]").val(),
                    error: function (xhr) {
                        console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
                that.message('');
            }
        };

        that.leaveChat = function () {
            that.publicChatView(null);
            that.activePollingXhr(null);
            resetUI();
        };

        function resetUI() {
            keepPolling = false;
            that.activePollingXhr(null);
            that.message('');
            that.messageIndex(0);
            that.chatContent('');
        }

        ///////////////////////////////////////////////

        that.contactView = function () {
            setActiveView(that.addContactView);
            that.firstname('');
            that.lastname('');
            that.users(null);
            that.leaveChat();
        };

        that.getUserContactsView = function () {
            setActiveView(that.userContactsView);
            that.leaveChat();
            that.getUserContacts();
        };

        that.searchContact = function () {
            if (that.firstname().trim() != '' || that.lastname().trim() != '') {
                var form = $("#searchContactForm");
                $.ajax({
                    url: form.attr("action"),
                    type: "GET",
                    data: {
                        'firstName': that.firstname(),
                        'lastName': that.lastname()
                    },
                    success: function (users) {
                        if (users.length > 0) {
                            that.users([]);
                            users.forEach(function (user) {
                                that.users.push(new User(user.id, user.firstname, user.lastname));
                            });
                        }
                    },
                    error: function (xhr) {
                        console.error("Error searching contact: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
            }
        };

        that.getUserContacts = function () {
            var form = $("#userContactsForm");
            $.ajax({
                url: form.attr("action"),
                type: "GET",
                success: function (contacts) {
                    that.userContacts([]);
                    contacts.forEach(function (contact) {
                        that.userContacts.push(new User(contact.id, contact.firstname, contact.lastname));
                    });
                },
                error: function (xhr) {
                    console.error("Error adding contact: status=" + xhr.status + ", statusText=" + xhr.statusText);
                }
            });
        };

        that.addContact = function () {
            setActiveView(that.addContactView);
            var form = $("#addUserForm");
            $.ajax({
                url: form.attr("action") + "?_csrf=" + $('input[name="_csrf"]').prop('value'),
                type: "POST",
                data: "contactId=" + this.id,
                success: function () {
                    setActiveView(that.userContactsView);
                    that.getUserContacts();
                },
                error: function (xhr) {
                    console.error("Error adding contact: status=" + xhr.status + ", statusText=" + xhr.statusText);
                }
            });
        };

        ///////////////////////////////////////////////

        that.newConversationForm = function () {
            that.leaveChat();
            that.getUserContacts();
            setActiveView(that.newConversationView);
        };

        function checkAsRead(message){
            $.ajax({
                url: "/chat/messages/read" + "?" +
                "_csrf=" + $('input[name="_csrf"]').prop('value'),
                type: "POST",
                data: "idMessage=" + message.id,
                error: function (xhr) {
                    console.error("Failed to mark message as read: status=" + xhr.status + ", statusText=" + xhr.statusText);
                }
            });
        }

        function pollForConversationMessages() {
            var form = $("#getPrivateMessageForm");
            that.activePollingPrivateXhr($.ajax({
                url: form.attr("action"),
                type: "GET",
                data: "idConversation=" + that.activeConversation().id,
                cache: false,
                success: function (messages) {
                    for (var i = 0; i < messages.length; i++) {
                        that.privateChatContent(that.privateChatContent() + messages[i].message + "\n");
                        checkAsRead(messages[i]);
                    }
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        console.error("Unable to retrieve chat messages. Chat ended.");
                    }
                },
                complete: pollForConversationMessages
            }));
            $('#privateMessage').focus();
        };

        that.addConversation = function () {
            if (that.selectedContacts == null || that.selectedContacts().length == 0) {
                alert("You have to select at least one contact");
                return;
            }
            var form = $("#createConversationForm");
            $.ajax({
                url: form.attr("action") + "?" +
                "title=" + that.title() + "&" +
                "message=" + that.firstMessage() + "&" +
                "_csrf=" + $('input[name="_csrf"]').prop('value'),
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify(that.selectedContacts()),
                success: function (conversation) {
                    that.title(conversation.title);
                    that.activeConversation(new Conversation(conversation.id, conversation.title, conversation.users, conversation.unreadMsgsCount));
                    pollForConversationMessages();
                    setActiveView(that.privateChatView);
                },
                error: function (xhr) {
                    console.error("Error adding conversation: status=" + xhr.status + ", statusText=" + xhr.statusText);
                }
            });
        };

        that.postPrivateMessage = function () {
            if (that.privateMessage().trim() != '') {
                var form = $("#postPrivateMessageForm");
                $.ajax({
                    url: form.attr("action") + "?_csrf=" + $('input[name="_csrf"]').prop('value'),
                    type: "POST",
                    data: "idConversation=" + that.activeConversation().id +
                    "message=" + $("#postPrivateMessageForm input[name=privateMessage]").val(),
                    error: function (xhr) {
                        console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
                that.privateMessage('');
            }
        };

    }

    //Activate knockout.js
    ko.applyBindings(new ChatViewModel());

});


