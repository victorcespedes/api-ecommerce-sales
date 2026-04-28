package com.nttdata.quarkus.repository;

import com.nttdata.quarkus.entity.Venta;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VentaRepository implements PanacheRepositoryBase<Venta,Long> {
}
