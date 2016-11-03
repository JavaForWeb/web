package bo.gob.asfi.entity;;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by fernando on 10/19/16.
 */

@NamedQueries({
	@NamedQuery(
		name = "TransferHQLwithLAZY",
		query = "select c from Transfer c"),


	@NamedQuery(
		name = "TransferHQLwithJoin",
		query = "select c from Transfer c left join fetch Account ")
})


@NamedNativeQueries({
	@NamedNativeQuery(
		name = "findAllTransferNativeSql",
		query = "select * from transfer limit 20",
		resultClass = Transfer.class
	)
})

@Entity
@Table(name="transfer", schema = "public")
public class Transfer
{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "source",
		foreignKey = @ForeignKey(name = "SOURCE_ID_FK")
	)
	private Account source;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "target",
		foreignKey = @ForeignKey(name = "TARGET_ID_FK")
	)
	private Account target;

	@Column(name="description")
	private String description;

	@Column(name="amount")
	private Integer amount;

	@Column(name="status")
	private String status;

	public Transfer()
	{
	}

	public Transfer(Integer id, Account source, Account target, Integer amount, String description, String status)
	{
		this.id = id;
		this.source = source;
		this.target = target;
		this.description = description;
		this.amount = amount;
		this.status = status;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Account getSource()
	{
		return source;
	}

	public void setSource(Account source)
	{
		this.source = source;
	}

	public Account getTarget()
	{
		return target;
	}

	public void setTarget(Account target)
	{
		this.target = target;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
