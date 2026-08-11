package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HotelHibImpl implements HotelModelInt {

	@Override
	public long add(HotelDTO dto) throws ApplicationException, DuplicateRecordException {
		
		Session session = null;
		Transaction tx = null;
		HotelDTO existDto = null;
		existDto = findByHotelName(dto.getHotelName());
		if (existDto != null) {
			throw new DuplicateRecordException("Hotel Name already exist");
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
			throw new ApplicationException("Exception in Hotel Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();
	}

	@Override
	public void delete(HotelDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Hotel Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(HotelDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		HotelDTO existDto = null;
		existDto = findByHotelName(dto.getHotelName());
		if (existDto != null) {
			throw new DuplicateRecordException("Hotel Name already exist");
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
			throw new ApplicationException("Exception in Hotel Add " + e.getMessage());
		} finally {
			session.close();
		}
	}
	
	@Override
	public HotelDTO findByHotelName(String hotelName) throws ApplicationException {

	    Session session = null;

	    try {
	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(HotelDTO.class);
	        criteria.add(Restrictions.like("hotelName", hotelName));

	        return (HotelDTO) criteria.uniqueResult();

	    } catch (HibernateException e) {
	        throw new ApplicationException("Exception in finding Hotel by Name");
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
			Criteria criteria = session.createCriteria(HotelDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Hotels list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(HotelDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<HotelDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HotelDTO.class);
			if (dto != null) {
				/*
				 * if (dto.getId() != null) { criteria.add(Restrictions.like("id",
				 * dto.getId())); }
				 */
	 			if (dto.getHotelName() != null && dto.getHotelName().length() > 0) {
					criteria.add(Restrictions.like("hotelName", dto.getHotelName()+ "%"));
				}
				if (dto.getLocation() != null && dto.getLocation().length() > 0) {
					criteria.add(Restrictions.like("location", dto.getLocation()+ "%"));
				}
				if (dto.getRating() > 0) {
				    criteria.add(Restrictions.eq("rating", dto.getRating()));
				}
				if (dto.getContactNo() != null && dto.getContactNo().length() > 0) {
					criteria.add(Restrictions.like("contactNo", dto.getContactNo()+ "%"));
				}
		
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<HotelDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Hotel search");
		} finally {
			session.close();
		}

		return list;
		
	}

	@Override
	public List search(HotelDTO dto) throws ApplicationException {
		return search (dto,0,0);
	}

	@Override
	public HotelDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		HotelDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (HotelDTO) session.get(HotelDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Hotel by pk");
		} finally {
			session.close();
		}

		return dto;
	}

}
