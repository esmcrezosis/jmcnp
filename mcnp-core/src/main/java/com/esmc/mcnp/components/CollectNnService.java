/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.components;

import com.esmc.mcnp.dto.desendettement.Collect;

/**
 *
 * @author USER
 */
public interface CollectNnService {

	public String collect_nn(Collect collect);

	public String collect_nn_det(Collect collect);

}
