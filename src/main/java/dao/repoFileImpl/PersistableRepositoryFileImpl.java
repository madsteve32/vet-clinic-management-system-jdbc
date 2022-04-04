package dao.repoFileImpl;

import dao.Identifiable;
import dao.idGenerator.IdGenerator;
import dao.impl.AbstractPersistableRepositoryImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class PersistableRepositoryFileImpl<K, V extends Identifiable<K>> extends AbstractPersistableRepositoryImpl<K, V> {
    private String dbFileName;

    public PersistableRepositoryFileImpl(IdGenerator<K> idGenerator, String dbFileName) {
        super(idGenerator);
        this.dbFileName = dbFileName;
    }

    @Override
    public void load() {
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(dbFileName)))) {
            clear();
            getIdGenerator().reset((K) in.readObject());
            addAll((Collection<V>) in.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(dbFileName)))) {
            out.writeObject(getIdGenerator().getCurrentId());
            out.writeObject(new ArrayList<>(findAll()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
