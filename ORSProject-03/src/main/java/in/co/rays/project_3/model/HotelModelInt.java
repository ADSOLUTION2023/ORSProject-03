package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HotelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface HotelModelInt {
	public long add(HotelDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(HotelDTO dto)throws ApplicationException;
	public void update(HotelDTO dto)throws ApplicationException,DuplicateRecordException;
	public HotelDTO findByPK(long pk)throws ApplicationException;
	public HotelDTO findByHotelName(String hotelName) throws ApplicationException,DuplicateRecordException ;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(HotelDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(HotelDTO dto)throws ApplicationException;

}
