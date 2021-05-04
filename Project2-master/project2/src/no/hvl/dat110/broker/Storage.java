package no.hvl.dat110.broker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	protected ConcurrentHashMap<String, ClientSession> clients;
	protected ConcurrentHashMap<String, ArrayList<Message>> offlineStorage;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		offlineStorage = new ConcurrentHashMap<String, ArrayList<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	public void addOfflineMessage(String user, Message msg){
		ArrayList<Message> newList = offlineStorage.get(user);
		newList.add(msg);
		offlineStorage.put(user, newList);
	}

	public ArrayList<Message> backOnline(String user){
		ArrayList<Message> returnList = offlineStorage.get(user);
		offlineStorage.remove(user);
		return returnList;
	}


	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		
		ClientSession session = new ClientSession(user, connection);
		clients.put(user, session);

		
		
	}

	public void removeClientSession(String user) {

		
		clients.remove(user);
		ArrayList<Message> offlineList = new ArrayList<Message>();
		offlineStorage.put(user, offlineList);
		
		
	}

	public void createTopic(String topic) {

		
		if (!subscriptions.contains(topic)) {
			Set<String> subscribers = new HashSet<String>();
			subscriptions.put(topic, subscribers);

		} else {
			System.out.println("***Topic already exists***");
		}
	}

	public void deleteTopic(String topic) {

	
		subscriptions.remove(topic);

		
	}

	public void addSubscriber(String user, String topic) {

		
		Set<String> newSet = getSubscribers(topic);
		newSet.add(user);
		subscriptions.put(topic, newSet);

		
	}

	public void removeSubscriber(String user, String topic) {

		
		Set<String> newSet = getSubscribers(topic);
		newSet.remove(user);
		subscriptions.put(topic, newSet);
	}
}