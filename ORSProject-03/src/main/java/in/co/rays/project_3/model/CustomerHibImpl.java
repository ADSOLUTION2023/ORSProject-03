package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CustomerDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CustomerHibImpl implements CustomerModelInt{

	@Override
	public long add(CustomerDTO dto) throws ApplicationException, DuplicateRecordException {
		
		Session session = null;
		Transaction tx = null;
		CustomerDTO existDto = null;
		existDto = findByAccountNo(dto.getAccountNo());
		if (existDto != null) {
			throw new DuplicateRecordException("Account Number already exist");
		}
		 session = HibDataSource.getSession();
		try {

			int pk = 0;
			tx = session.beginTransaction();

			session.save(dto);

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Customer Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();
	}

	@Override
	public void delete(CustomerDTO dto) throws ApplicationException {
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
	public void update(CustomerDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		CustomerDTO existDto = null;
		existDto = findByAccountNo(dto.getAccountNo());
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
			throw new ApplicationException("Exception in Customer Add " + e.getMessage());
		} finally {
			session.close();
		}
	}
	
	@Override
	public CustomerDTO findByAccountNo(String accountNo) throws ApplicationException {

	    Session session = null;

	    try {
	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(CustomerDTO.class);
	        criteria.add(Restrictions.eq("accountNo", accountNo));

	        return (CustomerDTO) criteria.uniqueResult();

	    } catch (HibernateException e) {
	        throw new ApplicationException("Exception in finding Customer by Account No");
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
			Criteria criteria = session.createCriteria(CustomerDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Customers list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(CustomerDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<CustomerDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CustomerDTO.class);
			if (dto != null) {
				/*
				 * if (dto.getId() != null) { criteria.add(Restrictions.like("id",
				 * dto.getId())); }
				 */
	 			if (dto.getAccountNo() != null && dto.getAccountNo().length() > 0) {
					criteria.add(Restrictions.like("accountNo", dto.getAccountNo() + "%"));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getBalance() > 0) {
				    criteria.add(Restrictions.eq("balance", dto.getBalance()));
				}
		
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<CustomerDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Customer search");
		} finally {
			session.close();
		}

		return list;
		
	}

	@Override
	public List search(CustomerDTO dto) throws ApplicationException {
		return search (dto,0,0);
	}


	@Override
	public CustomerDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		CustomerDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (CustomerDTO) session.get(CustomerDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Customer by pk");
		} finally {
			session.close();
		}

		return dto;
	}

}
