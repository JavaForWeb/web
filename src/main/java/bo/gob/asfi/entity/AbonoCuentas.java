package bo.gob.asfi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by fernando on 11/7/16.
 */

@Entity
@Table(name="abono_cuentas", schema = "public")

public class AbonoCuentas
{
	@ManyToOne
	@JoinColumn(name = "abono_sueldos_id",
		foreignKey = @ForeignKey(name = "ABONO_ID_FK")
	)
	AbonoSueldos abonoSueldos;

	@Column(name="name")
	String name;

	@Column(name="status")
	String status;

	@Column(name="amount")
	Integer amount;

	public AbonoCuentas()
	{

	}

	public AbonoCuentas(AbonoSueldos abonoSueldos, String name, String status, Integer amount)
	{
		this.abonoSueldos = abonoSueldos;
		this.name = name;
		this.status = status;
		this.amount = amount;
	}

	public AbonoSueldos getAbonoSueldos()
	{
		return abonoSueldos;
	}

	public void setAbonoSueldos(AbonoSueldos abonoSueldos)
	{
		this.abonoSueldos = abonoSueldos;
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

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}
}
