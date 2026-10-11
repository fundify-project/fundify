import Link from "next/link";

export default function NotFound() {
  return (
    <main className="flex min-h-[60vh] flex-col items-center justify-center px-6 text-center">
      <div className="text-6xl font-bold text-fg-3">404</div>
      <h1 className="mt-5 text-lg font-semibold text-fg">
        찾을 수 없는 페이지입니다
      </h1>
      <p className="mt-2 text-sm text-fg-2">
        존재하지 않는 종목이거나 잘못된 주소입니다.
      </p>
      <Link
        href="/"
        className="mt-7 rounded-xl bg-mint px-5 py-3 text-sm font-semibold text-[#062018] transition hover:bg-[#4ee0b2]"
      >
        홈으로 가기
      </Link>
    </main>
  );
}
