ALTER TABLE account_book ADD id UUID NOT NULL DEFAULT gen_random_uuid();
ALTER TABLE account_book ADD version INT NOT NULL DEFAULT 0;
ALTER TABLE account_book ADD PRIMARY KEY (id);
