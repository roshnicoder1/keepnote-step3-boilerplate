package com.stackroute.keepnote.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;

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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		Session session = sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return true;

	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		 boolean flag = true;
			try {
				if(getNoteById(noteId)==null) {
					flag = false;
				}else {
					Session session = sessionFactory.getCurrentSession();
				      Note n= session.byId(Note.class).load(noteId);
				      session.delete(n);
					  session.flush();
				}
			} catch (NoteNotFoundException e) {
				e.printStackTrace();
			}
			return flag;
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		String str = "From Note note where CreatedBy = :userId";
		Session s=sessionFactory.getCurrentSession();
		Query que = s.createQuery(str).setParameter("userId", userId);
		List ans = que.getResultList();
		return ans;

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note n= sessionFactory.getCurrentSession().find(Note.class, noteId);
		if(n==null) {
			throw new NoteNotFoundException("NoteNotFoundException");
		}
		else
			return n;

	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		Session session = sessionFactory.getCurrentSession();
	      Note n= session.byId(Note.class).load(note.getNoteId());
	      n.setNoteTitle(note.getNoteTitle());
	     n.setNoteContent(n.getNoteContent());
	      n.setCreatedBy(note.getCreatedBy());
	      n.setNoteStatus(n.getNoteStatus());
	     // n.setNoteCreatedAt(n.getCreatedAt());
	      n.setCategory(note.getCategory());
	      n.setReminder(note.getReminder());
	      session.flush();
	    return true;

	}

}
