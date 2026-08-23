"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function SearchBar() {
  const [keyword, setKeyword] = useState("");
  const router = useRouter();

  const handleSearch = () => {
    const trimmed = keyword.trim();
    if (!trimmed) return;

    router.push(`/search?keyword=${encodeURIComponent(trimmed)}`);
  };

  return (
    <div className="flex w-full items-center gap-2">
      <input
        type="text"
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
        onKeyDown={(e) => e.key === "Enter" && handleSearch()}
      />
      <button onClick={handleSearch}>검색</button>
    </div>
  );
}
