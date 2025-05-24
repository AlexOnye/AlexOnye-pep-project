package Controller;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
   
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);

        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessage);
        app.patch("/messages/{message_id}", this::handleUpdateMessage);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByUser);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    private void handleRegister(Context ctx) {
        Account acc = ctx.bodyAsClass(Account.class);
        Account created = accountService.register(acc);
        if (created != null) {
            ctx.json(created);
        } else {
            ctx.status(400);
        }
    }

    private void handleLogin(Context ctx) {
        Account acc = ctx.bodyAsClass(Account.class);
        Account loggedIn = accountService.login(acc);
        if (loggedIn != null) {
            ctx.json(loggedIn);
        } else {
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx) {
        Message msg = ctx.bodyAsClass(Message.class);
        Message created = messageService.createMessage(msg);
        if (created != null) {
            ctx.json(created);
        } else {
            ctx.status(400);
        }
    }

    private void handleGetAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void handleGetMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200); // Empty body, default behavior
        }
    }

    private void handleDeleteMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);
        if (deleted != null) {
            ctx.json(deleted);
        } else {
            ctx.status(200); // No content
        }
    }

    private void handleUpdateMessage(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message partialUpdate = ctx.bodyAsClass(Message.class);
        Message updated = messageService.updateMessageText(messageId, partialUpdate.getMessage_text());
        if (updated != null) {
            ctx.json(updated);
        } else {
            ctx.status(400);
        }
    }

    private void handleGetMessagesByUser(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(accountId);
        ctx.json(messages);
    }
  


}