package uk.ac.ncl.safecap.xmldata;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DataNamespace implements IDescribed, Cloneable {
	private String id;
	private String canonical_id;
	private String description;
	private List<Block> blocks;

	public DataNamespace() {
		blocks = new ArrayList<>();
	}

	public DataNamespace(String canonical_id, String id) {
		blocks = new ArrayList<>();
		this.id = id;
		this.canonical_id = canonical_id;
	}

	@Override
	@XmlElement
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Object clone() {
		final DataNamespace c = new DataNamespace();
		c.id = id;
		for (final Block block : blocks) {
			c.blocks.add((Block) block.clone());
		}

		return c;
	}

	public void buildFunctions(IFunctionBuilder builder) {
		for (final Block block : blocks) {
			block.buildFunctions(canonical_id, id, builder);
		}
	}

	@XmlElement
	public String getCononical_id() {
		return canonical_id;
	}

	public void setCononical_id(String canonical_id) {
		this.canonical_id = canonical_id;
	}

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public void cookTypes(TypeRegistry typeRegistry) {
	// for(Block block: blocks)
	// block.cookTypes(typeRegistry);
	// }

	public void addBlock(Block block) {
		blocks.add(block);
	}

	@XmlElement(name = "block")
	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public Block getBlockByName(Object id) {
		for (final Block block : blocks) {
			if (id.equals(block.getId())) {
				return block;
			}
		}

		return null;
	}

	public Block getBlockByNameOrNew(String kind, String id) {
		for (final Block block : blocks) {
			if (id.equals(block.getId())) {
				return block;
			}
		}

		final Block block = new Block(kind);
		block.enterAttribute("Id");
		block.enterValue(id);
		addBlock(block);
		return block;
	}

	public void inject(DataNamespace other) throws DataException {
		if (!other.getId().equals(id)) {
			throw new DataException("Namespace id mismatch");
		}

		for (final Block block : other.blocks) {
			final Object id = block.getId();
			final Block mine = getBlockByName(id);
			if (id != null && mine != null) {
				System.out.println("Redefinition of entity " + id);
				System.out.println("New:\n " + block);
				System.out.println("Old:\n " + mine);

				// throw new DataException("Redefinition of entity " + id);
			}
		}

		blocks.addAll(other.blocks);
	}

}
