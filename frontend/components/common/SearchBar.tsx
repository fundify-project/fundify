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
        placeholder="기업명 또는 종목코드를 검색하세요"
        className="flex-1 rounded-xl border border-line bg-ink-2 px-4 py-3.5 text-[15px] text-fg outline-none transition placeholder:text-fg-3 focus:border-mint-dim focus:ring-[3px] focus:ring-mint/10"
      />
      <button
        onClick={handleSearch}
        className="rounded-xl bg-mint px-5 py-3.5 text-[15px] font-semibold text-[#062018] transition hover:bg-[#4ee0b2]"
      >
        검색
      </button>
    </div>
  );
}
