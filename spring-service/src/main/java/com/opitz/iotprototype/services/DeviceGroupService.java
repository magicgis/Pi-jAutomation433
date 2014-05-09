package com.opitz.iotprototype.services;

import java.io.Serializable;
import java.util.List;

import com.opitz.iotprototype.entities.DeviceGroup;

/**
 * User: Pascal Date: 04.04.14 Time: 15:01
 */

public interface DeviceGroupService {

	public Serializable save(DeviceGroup devicegroup);

	public void update(DeviceGroup devicegroup);

	public void delete(Serializable id);

	public DeviceGroup findByLabel(String label);

	public List<DeviceGroup> listAll();

	public DeviceGroup findById(Serializable id);

	public void addUser(Integer groupId, String username);

	public void addPlug(Integer groupId, Integer plugId);
}
