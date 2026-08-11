package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PatientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PatientHibImpl implements PatientModelInt {

	@Override
	public long add(PatientDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		PatientDTO existDto = null;
		existDto = findByName(dto.getPatientName());
		if (existDto != null) {
			throw new DuplicateRecordException("Patient with name already exist");
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
			throw new ApplicationException("Exception in Patient Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();
	}

	@Override
	public void delete(PatientDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Patient Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(PatientDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		PatientDTO existDto = null;
		existDto = findByName(dto.getPatientName());
		if (existDto != null) {
			throw new DuplicateRecordException("Patient with name already exist");
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
			throw new ApplicationException("Exception in Patient Add " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public PatientDTO findByName(String patientName) throws ApplicationException {

		Session session = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PatientDTO.class);
			criteria.add(Restrictions.eq("patientName", patientName));

			return (PatientDTO) criteria.uniqueResult();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in finding Patient by Name");
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PatientDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Patients list");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(PatientDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		ArrayList<PatientDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PatientDTO.class);
			if (dto != null) {

			    if (dto.getPatientName() != null
			            && dto.getPatientName().length() > 0) {

			        criteria.add(
			            Restrictions.like(
			                "patientName",
			                dto.getPatientName() + "%"
			            )
			        );
			    }

			    if (dto.getDisease() != null
			            && dto.getDisease().length() > 0) {

			        criteria.add(
			            Restrictions.like(
			                "disease",
			                dto.getDisease() + "%"
			            )
			        );
			    }

			    if (dto.getDoctorName() != null
			            && dto.getDoctorName().length() > 0) {

			        criteria.add(
			            Restrictions.like(
			                "doctorName",
			                dto.getDoctorName() + "%"
			            )
			        );
			    }

			    if (dto.getAdmissionDate() != null) {

			        criteria.add(
			            Restrictions.eq(
			                "admissionDate",
			                dto.getAdmissionDate()
			            )
			        );
			    }
			}
		
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<PatientDTO>) criteria.list();
		}catch(

	HibernateException e)
	{
		throw new ApplicationException("Exception in Patient search");
	}finally
	{
		session.close();
	}

	return list;

	}

	@Override
	public List search(PatientDTO dto) throws ApplicationException {
		return search (dto,0,0);
	}

	@Override
	public PatientDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		PatientDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (PatientDTO) session.get(PatientDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Patient by pk");
		} finally {
			session.close();
		}

		return dto;
	}

}
