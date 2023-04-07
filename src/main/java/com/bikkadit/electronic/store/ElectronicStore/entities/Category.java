package com.bikkadit.electronic.store.ElectronicStore.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categories")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name="category_title",length = 60,nullable = false)
    private String title;

    @Column(name="category_description",length = 570,nullable = false)
    private String description;

    private String coverImage;

}
