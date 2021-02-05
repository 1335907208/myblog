package org.springblade.client.eneity;

import lombok.Data;
import org.springblade.core.tool.api.IResultCode;

@Data
public class ResultCode implements IResultCode {
	private  int code;
	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public int getCode() {
		return 0;
	}
}
