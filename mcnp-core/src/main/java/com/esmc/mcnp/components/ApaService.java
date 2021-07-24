/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.components;

import com.esmc.mcnp.dto.bc.Apa;

/**
 *
 * @author USER
 */
public interface ApaService {
	public boolean doApaRPG(Apa apa);

	public boolean doApaRPGPre(Apa apa);

	public boolean doApaI(Apa apa);

	public String doApaInrPre(Apa apa);

	public String doApaPreKit(Apa apa);

	public String doApaCncsnrPre(Apa apa);

	public String doApaPreKitRes(Apa apa);

	public String doApaCncsnrPreRes(Apa apa);

	public String doApaCncs(Apa apa);
}
