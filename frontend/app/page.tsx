export const dynamic = "force-dynamic";
import SearchBar from "@/components/common/SearchBar";
import PopularStocks from "@/components/common/PopularStocks";

export default function Home() {
  return (
    <main className="relative overflow-hidden px-6 py-24">
      {/* 배경 그라데이션 */}
      <div
        aria-hidden
        className="pointer-events-none absolute left-1/2 top-0 h-[520px] w-[820px] -translate-x-1/2 rounded-full bg-mint/[0.07] blur-[120px]"
      />

      <div className="relative mx-auto max-w-xl text-center">
        <span className="animate-fade-up inline-block rounded-full border border-mint/20 bg-mint/[0.08] px-3.5 py-1.5 text-xs font-medium uppercase tracking-widest text-mint">
          기업 분석 · 투자 인사이트
        </span>

        <h1
          className="animate-fade-up mt-6 text-4xl font-semibold leading-tight tracking-tight text-fg sm:text-5xl"
          style={{ animationDelay: "0.1s" }}
        >
          흩어진 기업 정보를
          <br />한 화면에서 <span className="text-mint">비교</span>하다
        </h1>

        <p
          className="animate-fade-up mx-auto mt-4 max-w-md text-base leading-relaxed text-fg-2"
          style={{ animationDelay: "0.2s" }}
        >
          재무제표, 시세, 투자 지표를 종목 하나로 모아 보고
          <br />
          업종 평균과 비교해 판단하세요.
        </p>

        <div
          className="animate-fade-up mt-10"
          style={{ animationDelay: "0.3s" }}
        >
          <SearchBar />
        </div>

        <div className="animate-fade-up" style={{ animationDelay: "0.4s" }}>
          <PopularStocks />
        </div>
      </div>
    </main>
  );
}
