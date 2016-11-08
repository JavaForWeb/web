package bo.gob.asfi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fernando on 11/7/16.
 */

@Entity
@Table(name="abono_sueldos", schema = "public")

public class AbonoSueldos
{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id", unique=true)
	Integer id;


	@Column(name="name")
	String name;

	@Column(name="status")
	String status;

	@Column(name="date")
	String date;

	@Column(name="total")
	Integer total;

	@Column(name="total_abonado")
	Integer totalAbonado;

	@Column(name="total_rechazado")
	Integer totalRechazado;

	public AbonoSueldos()
	{

	}

	public AbonoSueldos(String name, String status, String date, Integer total, Integer totalAbonado, Integer totalRechazado)
	{
		this.name = name;
		this.status = status;
		this.date = date;
		this.total = total;
		this.totalAbonado = totalAbonado;
		this.totalRechazado = totalRechazado;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getTotalAbonado()
	{
		return totalAbonado;
	}

	public void setTotalAbonado(Integer totalAbonado)
	{
		this.totalAbonado = totalAbonado;
	}

	public Integer getTotalRechazado()
	{
		return totalRechazado;
	}

	public void setTotalRechazado(Integer totalRechazado)
	{
		this.totalRechazado = totalRechazado;
	}
}
