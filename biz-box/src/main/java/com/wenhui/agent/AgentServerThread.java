package com.wenhui.agent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AgentServerThread extends Thread {
    private Socket client;
    private final Agent agent;

    AgentServerThread(final Agent agent, final Socket client) {
        super(agent.getName() + System.currentTimeMillis());
        this.agent = agent;
        this.client = client;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
            Message message = new Message.MessageBuilder().withSender(agent.getPort()).withType(Message.MESSAGE_TYPE.READY).build();
            out.writeObject(message);
            Object fromClient;
            while ((fromClient = in.readObject()) != null) {
                if (fromClient instanceof Message) {
                    final Message msg = (Message) fromClient;
                    System.out.println(String.format("%d received: %s", agent.getPort(), fromClient.toString()));
                    if (Message.MESSAGE_TYPE.INFO_NEW_BLOCK == msg.type) {
                        if (msg.blocks.isEmpty() || msg.blocks.size() > 1) {
                            System.err.println("Invalid block received: " + msg.blocks);
                        }
                        synchronized (agent) {
                            agent.addBlock(msg.blocks.get(0));
                        }
                        break;
                    } else if (Message.MESSAGE_TYPE.REQ_ALL_BLOCKS == msg.type) {
                        out.writeObject(new Message.MessageBuilder()
                                .withSender(agent.getPort())
                                .withType(Message.MESSAGE_TYPE.RSP_ALL_BLOCKS)
                                .withBlocks(agent.getBlocks())
                                .build());
                        break;
                    }
                }
            }
            client.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
