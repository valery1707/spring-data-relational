/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.jdbc.core.convert.sqlgeneration;

import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.RenderContextFactory;
import org.springframework.data.relational.core.mapping.PersistentPropertyPathExtension;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.render.RenderContext;
import org.springframework.data.relational.core.sql.render.SqlRenderer;

public class AnalyticSqlGenerator {

	private final Dialect dialect;

	private final AnalyticStructureBuilder<RelationalPersistentEntity, PersistentPropertyPathExtension>.Select selectStructure;
	private final StructureToSelect structureToSelect;

	public AnalyticSqlGenerator(Dialect dialect, AggregateToStructure aggregateToStructure,
			StructureToSelect structureToSelect, RelationalPersistentEntity<?> aggregate) {

		this.dialect = dialect;
		this.selectStructure = aggregateToStructure.createSelectStructure(aggregate);
		this.structureToSelect = structureToSelect;
	}

	public String findAll(RelationalPersistentEntity<?> aggregateRoot) {

		Select select = structureToSelect.createSelect(selectStructure).findAll();

		return getSqlRenderer().render(select);
	}

	public <T> String findById(RelationalPersistentEntity<T> aggregateRoot) {

		Select select = structureToSelect.createSelect(selectStructure)
				.findById();
		return getSqlRenderer().render(select);
	}

	public <T> String findAllById(RelationalPersistentEntity<T> aggregateRoot) {

		Select select = structureToSelect.createSelect(selectStructure)
				.findAllById();
		return getSqlRenderer().render(select);
	}

	private SqlRenderer getSqlRenderer() {

		RenderContext renderContext = new RenderContextFactory(dialect).createRenderContext();
		return SqlRenderer.create(renderContext);
	}
}
