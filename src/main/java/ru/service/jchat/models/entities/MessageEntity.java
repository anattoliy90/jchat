package ru.service.jchat.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "messages", schema = "jchat")
public class MessageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_generator")
    @SequenceGenerator(name = "message_id_generator", schema = "jchat", sequenceName = "message_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @Column(name = "text")
    private String text;

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ChatEntity getChat() {
        return chat;
    }

    public void setChat(ChatEntity chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }
}
