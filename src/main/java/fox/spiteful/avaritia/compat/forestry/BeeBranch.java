package fox.spiteful.avaritia.compat.forestry;

import java.util.ArrayList;

import net.minecraft.util.StatCollector;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

public enum BeeBranch implements IClassification {

    BALANCED("balanced", "Molestus"),
    INFINITE("infinite", "Infinitus");

    private final String name;
    private final String latin;
    private final ArrayList<IAlleleSpecies> species = new ArrayList<>();
    private IClassification parent;
    private final EnumClassLevel level = EnumClassLevel.GENUS;

    BeeBranch(String nombre, String science) {
        name = nombre;
        latin = science;
        parent = AlleleManager.alleleRegistry.getClassification("family.apidae");
        AlleleManager.alleleRegistry.registerClassification(this);
    }

    @Override
    public EnumClassLevel getLevel() {
        return level;
    }

    @Override
    public String getUID() {
        return "classification." + name;
    }

    @Override
    public String getName() {
        return StatCollector.translateToLocal("classification." + name);
    }

    @Override
    public String getScientific() {
        return latin;
    }

    @Override
    public String getDescription() {
        return StatCollector.translateToLocal("classification." + name + ".desc");
    }

    @Override
    public IClassification[] getMemberGroups() {
        return null;
    }

    @Override
    public void addMemberGroup(IClassification group) {

    }

    @Override
    public IAlleleSpecies[] getMemberSpecies() {
        return this.species.toArray(new IAlleleSpecies[0]);
    }

    @Override
    public void addMemberSpecies(IAlleleSpecies species) {
        if (!this.species.contains(species)) this.species.add(species);
    }

    @Override
    public IClassification getParent() {
        return this.parent;
    }

    @Override
    public void setParent(IClassification parent) {
        this.parent = parent;
    }

}
