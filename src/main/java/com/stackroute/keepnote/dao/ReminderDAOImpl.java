package com.stackroute.keepnote.dao;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Reminder;
/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class ReminderDAOImpl implements ReminderDAO {
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	/*
	 * Create a new reminder
	 */
	public boolean createReminder(Reminder reminder) {
		Session session = sessionFactory.getCurrentSession();
		session.save(reminder);
		session.flush();
		return true;
	}
	/*
	 * Update an existing reminder
	 */
	public boolean updateReminder(Reminder reminder) {
		 Session session = sessionFactory.getCurrentSession();
	      Reminder rem2 = session.byId(Reminder.class).load(reminder.getReminderId());
	      rem2.setReminderName(reminder.getReminderName());
	      rem2.setReminderType(reminder.getReminderType());
	      rem2.setReminderDescription(reminder.getReminderDescription());
	      rem2.setReminderCreatedBy(reminder.getReminderCreatedBy());
	      rem2.setReminderCreationDate(new Date());
	      session.flush();
	    return true;
	}
	/*
	 * Remove an existing reminder
	 */
	public boolean deleteReminder(int reminderId) {
		boolean flag = true;
		try {
			if(getReminderById(reminderId)==null) {
				flag = false;
			}else {
				Session session = sessionFactory.getCurrentSession();
			      Reminder rem= session.byId(Reminder.class).load(reminderId);
			      session.delete(rem);
				  session.flush();
			}
		} catch (ReminderNotFoundException e) {
			return false;
//			throw new CategoryNotFoundException("CategoryNotFoundException");
		}
		return flag;
	}
	/*
	 * Retrieve details of a specific reminder
	 */
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Reminder reminder  = sessionFactory.getCurrentSession().find(Reminder.class,reminderId);
		if(reminder==null) {
			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
		else
			return reminder;
	}
	/*
	 * Retrieve details of all reminders by userId
	 */
	public List<Reminder> getAllReminderByUserId(String userId) {
		Session s=sessionFactory.getCurrentSession();
		Query que = s.createQuery("From Reminder reminder where reminderCreatedBy = :userId").setParameter("userId", userId);
		List<Reminder> ans = que.getResultList();
		return ans;
	}
}