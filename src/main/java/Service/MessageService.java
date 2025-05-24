package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO = new MessageDAO();

    public Message createMessage(Message msg) {
        if (msg.getMessage_text() == null || msg.getMessage_text().isBlank() || msg.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.insertMessage(msg);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        Message msg = messageDAO.getMessageById(messageId);
        if (msg != null) {
            messageDAO.deleteMessage(messageId);
        }
        return msg;
    }

    public Message updateMessageText(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }
        Message updated = messageDAO.updateMessageText(messageId, newText);
        return updated;
    }

    public List<Message> getMessagesByAccount(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
