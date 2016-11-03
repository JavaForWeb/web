package bo.gob.asfi.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by fernando on 10/29/16.
 */

@Entity
@Table(name="upload", schema = "public")

public class UploadFile
{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id", unique=true)
	private Integer id;

	@Column(name="filename")
	private String name;

	@Column(name="mime_type")
	private String mimeType;

	// The first approach stores the file directly in the column. The type of such a column is bytea --> @Type
	// The second approach is to store a OID in the column which references a file.  --> @Lob

	@Lob
	//@Type(type="org.hibernate.type.BinaryType")
	@Column(name="content")
	private byte[] content;

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

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType(String mimeType)
	{
		this.mimeType = mimeType;
	}

	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}
}
