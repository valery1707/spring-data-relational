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

package org.springframework.data.relational.core.mapping;

import java.util.function.Predicate;

import org.springframework.data.mapping.PersistentPropertyPath;
import org.springframework.lang.Nullable;

/**
 * Represents a path within an aggregate starting from the aggregate root.
 * <p>
 * The path implements {@link Iterable} to iterate over all path segments including the path root.
 *
 * @since 3.2
 * @author Jens Schauder
 * @author Mark Paluch
 */
public interface AggregatePath extends Iterable<AggregatePath> {

	/**
	 * Returns {@code true} if the current path is a root path element (i.e. {@link #getLength()} equals zero) or
	 * {@code false} if the path points to a leaf property.
	 *
	 * @return {@code true} if the current path is a root path element or {@code false} if the path points to a leaf
	 *         property.
	 */
	boolean isRoot();

	/**
	 * Returns the path that has the same beginning but is one segment shorter than this path.
	 *
	 * @return the parent path. Guaranteed to be not {@literal null}.
	 * @throws IllegalStateException when called on an empty path.
	 */
	AggregatePath getParentPath();

	/**
	 * The {@link RelationalPersistentEntity} associated with the leaf of this path.
	 *
	 * @return Might return {@literal null} when called on a path that does not represent an entity.
	 */
	@Nullable
	RelationalPersistentEntity<?> getLeafEntity();

	/**
	 * The {@link RelationalPersistentEntity} associated with the leaf of this path or throw {@link IllegalStateException}
	 * if the leaf cannot be resolved.
	 *
	 * @return the required {@link RelationalPersistentEntity} associated with the leaf of this path.
	 * @throws IllegalStateException if the persistent entity cannot be resolved.
	 */
	RelationalPersistentEntity<?> getRequiredLeafEntity();

	RelationalPersistentProperty getRequiredIdProperty();

	int getLength();

	/**
	 * Returns {@literal true} exactly when the path is non-empty and the leaf property an embedded one.
	 *
	 * @return if the leaf property is embedded.
	 */
	boolean isEmbedded();

	/**
	 * @return {@literal true} when this is an empty path or the path references an entity.
	 */
	boolean isEntity();

	String toDotPath();

	/**
	 * Returns {@literal true} if there are multiple values for this path, i.e. if the path contains at least one element
	 * that is a collection and array or a map.
	 *
	 * @return {@literal true} if the path contains a multivalued element.
	 */
	boolean isMultiValued();

	/**
	 * @return {@literal true} if the leaf property of this path is a {@link java.util.Map}.
	 * @see RelationalPersistentProperty#isMap()
	 */
	boolean isMap();

	/**
	 * @return {@literal true} when this is references a {@link java.util.List} or {@link java.util.Map}.
	 */
	boolean isQualified();

	RelationalPersistentProperty getRequiredLeafProperty();

	RelationalPersistentProperty getBaseProperty();

	/**
	 * @return {@literal true} when this is references a {@link java.util.Collection} or an array.
	 */
	boolean isCollectionLike();

	/**
	 * @return whether the leaf end of the path is ordered, i.e. the data to populate must be ordered.
	 * @see RelationalPersistentProperty#isOrdered()
	 */
	boolean isOrdered();

	/**
	 * Creates a new path by extending the current path by the property passed as an argument.
	 *
	 * @param property must not be {@literal null}.
	 * @return Guaranteed to be not {@literal null}.
	 */
	AggregatePath append(RelationalPersistentProperty property);

	PersistentPropertyPath<? extends RelationalPersistentProperty> getRequiredPersistentPropertyPath();

	/**
	 * Filter the {@link AggregatePath} hierarchy by walking all path segment from the leaf to {@link #isRoot() root}
	 * applying the given filter {@link Predicate}. Returns a matching {@link AggregatePath} or {@literal null} if the
	 * filter predicate does not match any path segment.
	 *
	 * @param predicate
	 * @return the matched aggregate path element or {@code null} if the filter predicate does not match any path segment.
	 */
	@Nullable
	default AggregatePath filter(Predicate<AggregatePath> predicate) {

		AggregatePath path = this;
		while (!predicate.test(path)) {

			if (path.isRoot()) {
				path = null;
				break;
			}
			path = path.getParentPath();
		}

		return path;
	}

}