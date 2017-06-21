package reminder.dao;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import reminder.entity.Reminder;
import reminder.entity.Status;

/**
 * DAO class for DB interaction
 * 
 * @author irfan
 *
 */
public class ReminderDAO {
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Return all reminders, apply filters if applicable
	 * 
	 * @param status
	 *            - Reminder status
	 * @param startDate
	 *            - start due-date filter
	 * @param endDate
	 *            - end due-date filter
	 * @return All matched reminder or empty list
	 */
	public List<Reminder> getAllReminders(String status, String startDate, String endDate) {
		EntityManager entityManager = ReminderEnitityManager.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			String q = status != null ? "from Reminder where status = :status" : "from Reminder";
			Query query;

			if (startDate != null && endDate != null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
				if (status == null) {
					q += " Where dueDate between :startDate  AND :endDate";
				} else
					q += " AND  dueDate between :startDate  AND :endDate";

				query = entityManager.createQuery(q);
				query.setParameter("startDate", simpleDateFormat.parse(startDate));
				query.setParameter("endDate", simpleDateFormat.parse(endDate));

			} else
				query = entityManager.createQuery(q);

			if (status != null)
				query.setParameter("status", Status.valueOf(status));

			@SuppressWarnings("unchecked")
			List<Reminder> reminders = query.getResultList();
			entityManager.getTransaction().commit();
			return reminders != null ? reminders : Collections.emptyList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
		}
		return Collections.emptyList();
	}

	/** Create reminder object in DB
	 * @param reminder - new entity
	 * @return id of created reminder
	 */
	public long addReminder(Reminder reminder) {
		EntityManager entityManager = ReminderEnitityManager.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Reminder r = entityManager.merge(reminder);
			entityManager.getTransaction().commit();
			return r.getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
		}
		return -1;
	}

	/** Return reminder by ID
	 * @param id - Id to search for
	 * @return Reminder or null 
	 */
	public Reminder getById(long id) {
		EntityManager entityManager = ReminderEnitityManager.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Reminder reminder = entityManager.find(Reminder.class, id);
			entityManager.getTransaction().commit();
			return reminder;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
		}
		return null;
	}

	/** Update existing reminder
	 * @param id
	 * @param updatedReminder
	 * @return update status as boolean
	 */
	public boolean updateReminder(long id, Reminder updatedReminder) {
		EntityManager entityManager = ReminderEnitityManager.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Reminder reminder = entityManager.find(Reminder.class, id);
			if (reminder == null)
				return false;
			reminder.setName(updatedReminder.getName() != null ? updatedReminder.getName() : reminder.getName());
			reminder.setDescription(updatedReminder.getDescription() != null ? updatedReminder.getDescription()
					: reminder.getDescription());
			reminder.setDueDate(
					updatedReminder.getDueDate() != null ? updatedReminder.getDueDate() : reminder.getDueDate());
			reminder.setStatus(
					updatedReminder.getStatus() != null ? updatedReminder.getStatus() : reminder.getStatus());
			entityManager.merge(reminder);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
		}
		return false;
	}

	/** Delete by id
	 * @param id
	 * @return
	 */
	public boolean deleteReminder(long id) {
		EntityManager entityManager = ReminderEnitityManager.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Reminder reminder = entityManager.find(Reminder.class, id);
			entityManager.remove(reminder);
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
		}
		return false;
	}

}
