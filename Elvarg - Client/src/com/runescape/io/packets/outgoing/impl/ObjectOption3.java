package com.runescape.io.packets.outgoing.impl;

import com.runescape.io.ByteBuffer;
import com.runescape.io.packets.outgoing.OutgoingPacket;

public class ObjectOption3 implements OutgoingPacket {

	int id;
	int val1;
	int val2;

	public ObjectOption3(int val1, int val2, int id) {
		this.id = id;
		this.val1 = val1;
		this.val2 = val2;
	}

	@Override
	public void buildPacket(ByteBuffer buf) {
		buf.putOpcode(70);
		buf.writeUnsignedWordBigEndian(val1);
		buf.putShort(val2);
		buf.writeSignedBigEndian(id);
	}
}
