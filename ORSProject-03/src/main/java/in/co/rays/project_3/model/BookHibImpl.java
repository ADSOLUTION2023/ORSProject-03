package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BookHibImpl implements BookModelInt{
	@Override
	public long add(BookDTO dto) throws ApplicationException, DuplicateRecordException {

	    Session session = null;
	    Transaction tx = null;

	    BookDTO existDto = findByTitle(dto.getTitle());

	    if (existDto != null) {
	        throw new DuplicateRecordException("Title already exists");
	    }

	    session = HibDataSource.getSession();

	    try {

	        tx = session.beginTransaction();

	        session.save(dto);

	        tx.commit();

	    } catch (HibernateException e) {

	        if (tx != null) {
	            tx.rollback();
	        }

	        e.printStackTrace();

	        throw new ApplicationException(
	                "Exception in Book Add " + e.getMessage());

	    } finally {

	        if (session != null) {
	            session.close();
	        }
	    }

	    return dto.getId();
	}
	@Override
	public void delete(BookDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(BookDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		BookDTO existDto = null;
		existDto = findByTitle(dto.getTitle());
		if (existDto != null) {
			throw new DuplicateRecordException("Account Number id already exist");
		}
		 session = HibDataSource.getSession();
		try {

			tx = session.beginTransaction();

			session.saveOrUpdate(dto);

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Book Add " + e.getMessage());
		} finally {
			session.close();
		}
	}
	
	@Override
	public BookDTO findByTitle(String Title) throws ApplicationException {

	    Session session = null;

	    try {
	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(BookDTO.class);
	        criteria.add(Restrictions.eq("Title", Title));

	        return (BookDTO) criteria.uniqueResult();

	    } catch (HibernateException e) {
	        throw new ApplicationException("Exception in finding Book by Account No");
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}
	@Override
	public List list() throws ApplicationException {
	
		return list(0,0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BookDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Books list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(BookDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<BookDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BookDTO.class);
			if (dto != null) {
				
				 if (dto.getId() != null) { criteria.add(Restrictions.like("id",
				 dto.getId())); 
				 }
				 
				if (dto.getTitle() != null && dto.getTitle().length() > 0) {
				    criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}

				if (dto.getAuthor() != null && dto.getAuthor().length() > 0) {
				    criteria.add(Restrictions.like("author", dto.getAuthor() + "%"));
				}

				if (dto.getPrice() != null) {
				    criteria.add(Restrictions.eq("price", dto.getPrice()));
				}

				if (dto.getPublicationYear() != null) {
				    criteria.add(Restrictions.eq("publicationYear", dto.getPublicationYear()));
				}
		
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<BookDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Book search");
		} finally {
			session.close();
		}

		return list;
		
	}

	@Override
	public List search(BookDTO dto) throws ApplicationException {
		return search (dto,0,0);
	}


	@Override
	public BookDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		BookDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (BookDTO) session.get(BookDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Book by pk");
		} finally {
			session.close();
		}

		return dto;
	}
	
	
}
